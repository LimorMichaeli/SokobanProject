package controller.commands;

import model.IModel;
import view.IView;

public class DisplayCommand extends SokobanCommand {

	private IModel model;
	private IView view;
	
	
	public DisplayCommand(IModel model,IView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void execute() {
		view.displayLevel(model.getCurrentLevel(),(Integer) getParams().remove(0));		
	}



}
