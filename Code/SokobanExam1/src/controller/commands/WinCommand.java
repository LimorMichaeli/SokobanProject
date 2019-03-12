package controller.commands;

import view.IView;

public class WinCommand extends SokobanCommand {

	private IView view;

	public WinCommand(IView view) {
		this.view = view;
	}

	@Override
	public void execute() {
		view.userWin();
	}

}
