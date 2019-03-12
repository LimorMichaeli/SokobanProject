package controller.commands;

import model.IModel;

public class SaveCommand extends SokobanCommand {

	IModel model;
	
	public SaveCommand(IModel model) {
		this.model = model;
	}

	@Override
	public void execute() {
		model.saveLevel((String)getParams().get(0));		
	}

}
