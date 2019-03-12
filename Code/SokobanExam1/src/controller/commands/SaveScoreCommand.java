package controller.commands;

import model.IModel;

public class SaveScoreCommand extends SokobanCommand {

	private IModel model;

	public SaveScoreCommand(IModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		model.saveScore((String) getParams().get(0));
	}

}
