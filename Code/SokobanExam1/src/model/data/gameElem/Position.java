package model.data.gameElem;

import java.io.Serializable;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	public Position() {
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
