package controller.commands;

import model.IModel;

public class MoveCommand extends SokobanCommand {

	private IModel model;
	
	
	public MoveCommand(IModel model) {
		this.model=model;
	}
	

	@Override
	public void execute() {	
		model.movePlayer((String)getParams().get(0));		
	}


}
