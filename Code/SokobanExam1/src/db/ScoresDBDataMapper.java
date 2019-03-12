package db;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.serverProtocol.Command;

public class ScoresDBDataMapper implements IScoresDataMapper {
	
	private int serverPort;
	private String serverHost;

	public ScoresDBDataMapper(String host, int port) {
		this.serverHost = host;
		this.serverPort = port;
	}

	@Override
	public void save(Score score) {
		Command cmd = new Command();
		cmd.setName("addScore");
		cmd.setScore(score);
		try {
			Socket socket = new Socket(this.serverHost, this.serverPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			out.writeObject(cmd);
			out.flush();

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Score[] search(ScoresQuery query) {
		Command cmd = new Command();
		cmd.setName("searchScores");
		cmd.setQuery(query);
		Score[] resp = null;
		try {
			Socket socket = new Socket(this.serverHost, this.serverPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			out.writeObject(cmd);
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			resp = (Score[]) in.readObject();

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
