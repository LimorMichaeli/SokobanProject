package controller.commands;

import db.Score;
import view.IView;

public class ResponeScoresCommand extends SokobanCommand {

	private IView view;

	public ResponeScoresCommand(IView view) {
		this.view = view;
	}

	@Override
	public void execute() {
		this.view.setScores((Score[]) getParams().get(0));
	}

}
