package controller.commands;

import view.IView;

public class TimeCommand extends SokobanCommand {

	public IView view;

	public TimeCommand(IView view) {
		this.view = view;
	}

	@Override
	public void execute() {
		view.updateTime((int) getParams().get(0));
	}

}
