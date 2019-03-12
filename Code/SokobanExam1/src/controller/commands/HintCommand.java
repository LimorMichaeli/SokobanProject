package controller.commands;

import java.util.LinkedList;

import model.IModel;
import model.serverProtocol.SolutionRespone;
import view.IView;

public class HintCommand extends SokobanCommand {

	private IModel model;
	private IView view;

	public HintCommand(IModel model, IView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void execute()
	{
		SolutionRespone solution = this.model.getSolution();
		if (solution.getSolvable()) {
			if (solution.getActions().length > 0)
				this.view.setSolution(new String[] { solution.getActions()[0] });
		} else {
			LinkedList<String> linked = new LinkedList<>();
			linked.add("Failed to find solution for this state.");
			this.view.displayMessage(new LinkedList<String>(linked));
		}
	}

}
