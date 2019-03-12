package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

import db.Score;
import db.ScoresDBDataMapper;
import db.SolutionsGateway;
import model.data.policy.MySokobanPolicy;
import model.serverProtocol.Command;
import model.serverProtocol.SolutionRespone;
import plannable.Action;
import planner.CantSolvedException;
import planner.Strips;
import strips.MyHeuristic;
import strips.StripsAdapter;

public class PeerCallable extends Observable implements Callable<Boolean> {

	private InputStream inputStream;
	private OutputStream outputStream;
	private Peer peer;
	private SolutionsGateway solutionGateway;

	public PeerCallable(Socket socket, Peer peer, SolutionsGateway solutionGateway) {
		this.peer = peer;
		this.solutionGateway = solutionGateway;
		try {
			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean call() throws Exception {
		ObjectClientInput clientInput = new ObjectClientInput();
		Command command = clientInput.read(this.inputStream);

		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(this.outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		switch (command.getName()) {
		case "searchScores":
			this.peer.setStatusProperty("Search scores");
			ScoresDBDataMapper searchDataMapper = new ScoresDBDataMapper();
			Score[] scores = searchDataMapper.search(command.getQuery());
			try {
				out.writeObject(scores);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "addScore":
			this.peer.setStatusProperty("Add score to database");
			ScoresDBDataMapper scoreDataMapper = new ScoresDBDataMapper();
			scoreDataMapper.save(command.getScore());
			break;
		case "solution":
			this.peer.setStatusProperty("Return solution for level");
			int hashCode = command.getLevel().hashCode();
			SolutionRespone respone = null;
			if (this.solutionGateway.isExist(hashCode)) {
				respone = this.solutionGateway.GetSolution(hashCode);
			} else {
				respone = new SolutionRespone();
				String[] actionsCommand = null;
				StripsAdapter solver = new StripsAdapter(command.getLevel());
				MyHeuristic heuristic = new MyHeuristic(command.getLevel(), new MySokobanPolicy());
				boolean canSolved = true;
				Strips strips = new Strips(solver, heuristic);
				try {
					List<Action> actions = strips.computePlan();
					actionsCommand = new String[actions.size()];
					for (int i = 0; i < actionsCommand.length; i++)
						actionsCommand[i] = (actions.get(i).toString());
				} catch (CantSolvedException e) {
					canSolved = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
				respone.setSolvable(canSolved);
				respone.setActions(actionsCommand);
				this.solutionGateway.saveSolution(hashCode, canSolved, actionsCommand);
			}
			try {
				out.writeObject(respone);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		}

		setChanged();
		notifyObservers(this.peer);
		return true;
	}

}
