package controller.commands;

import controller.Controller;
import model.IModel;
import view.IView;

public class ExitCommand extends SokobanCommand {

	IModel model;
	IView view;
	Controller controller;
	
	public ExitCommand(IModel model, IView view, Controller controller) {

		this.model = model;
		this.view = view;
		this.controller = controller;
	}
	@Override
	public void execute() {

		controller.stop();
	
		
	}

}
