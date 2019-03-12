package model.data.gameElem;

import java.io.Serializable;

public abstract class GeneralElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Position pos;

	public GeneralElement() {}
	
	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
}
