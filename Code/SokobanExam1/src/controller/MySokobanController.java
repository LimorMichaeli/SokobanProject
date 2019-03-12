package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import controller.commands.DisplayCommand;
import controller.commands.ExitCommand;
import controller.commands.HintCommand;
import controller.commands.ICommand;
import controller.commands.LoadCommand;
import controller.commands.MoveCommand;
import controller.commands.RequestScoresCommand;
import controller.commands.ResponeScoresCommand;
import controller.commands.SaveCommand;
import controller.commands.SaveScoreCommand;
import controller.commands.SolutionCommand;
import controller.commands.TimeCommand;
import controller.commands.WinCommand;
import controller.server.MyServer;
import model.IModel;
import view.IView;

public class MySokobanController implements Observer {

	private Controller controller;
	private IModel model;
	private IView view;
	private Map<String, ICommand> commands;
	MyServer myServer;

	public void setMyServer(MyServer myServer) {
		this.myServer = myServer;
		myServer.start();
	}

	public MySokobanController(IModel model, IView view) {
		controller = new Controller();
		this.model = model;
		this.view = view;
		commands = new HashMap<String, ICommand>();
		initCommands();
		controller.start();
	}

	public void update(Observable arg0, Object arg) {
		List<Object> params = (List<Object>) arg;
		String commandKey = (String) params.remove(0);
		ICommand c = commands.get(commandKey);
		if (c == null) {
			return;
		}
		c.setParams(params);
		controller.insertCommand(c);
	}

	private void initCommands() {
		commands.put("Move", new MoveCommand(model));
		commands.put("Display", new DisplayCommand(model, view));
		commands.put("Load", new LoadCommand(model));
		commands.put("Save", new SaveCommand(model));
		commands.put("UpdateSeconds", new TimeCommand(view));
		commands.put("Win", new WinCommand(view));
		commands.put("SaveScore", new SaveScoreCommand(model));
		commands.put("ResponeScores", new ResponeScoresCommand(view));
		commands.put("RequestScores", new RequestScoresCommand(model));
		commands.put("Solution", new SolutionCommand(model,view));
		commands.put("Hint", new HintCommand(model,view));
		commands.put("Exit", new ExitCommand(model, view, controller));

	}

}
