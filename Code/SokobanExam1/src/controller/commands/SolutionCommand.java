package controller.commands;

import java.util.LinkedList;

import model.IModel;
import model.serverProtocol.SolutionRespone;
import view.IView;

public class SolutionCommand extends SokobanCommand {

	private IModel model;
	private IView view;

	public SolutionCommand(IModel model, IView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void execute() {
		SolutionRespone solution = this.model.getSolution();
		if (solution.getSolvable())
			this.view.setSolution(solution.getActions());
		else {
			LinkedList<String> linked = new LinkedList<>();
			linked.add("Failed to find solution for this state.");
			this.view.displayMessage(new LinkedList<String>(linked));
		}
	}

}
