package controller.commands;

import db.ScoresQuery;
import model.IModel;

public class RequestScoresCommand extends SokobanCommand {

	private IModel model;

	public RequestScoresCommand(IModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		model.searchScores((ScoresQuery) getParams().get(0));
	}

}
