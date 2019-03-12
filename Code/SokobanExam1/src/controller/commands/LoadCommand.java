package controller.commands;

import model.IModel;

public class LoadCommand extends SokobanCommand {

	private IModel model;

	
	public LoadCommand(IModel model) {
		this.model =model;
	}
	@Override
	public void execute() {
		model.loadLevel((String)getParams().get(0));
		
	}



}
