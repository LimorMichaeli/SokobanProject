package controller.commands;

import java.util.List;

public abstract class SokobanCommand implements ICommand {
	
	private List<Object> params;

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

}
