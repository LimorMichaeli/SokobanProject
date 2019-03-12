package model.data.gameElem;

import java.io.Serializable;
import java.util.ArrayList;

public class Cell implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public char symbol;
	private ArrayList<GeneralElement> elements = new ArrayList<GeneralElement>();

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public ArrayList<GeneralElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<GeneralElement> elements) {
		// TODO Auto-generated method stub
		this.elements = elements;
	}

	// policy
	public boolean hasWall() {
		for (GeneralElement el : elements) {
			if (el instanceof Wall) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBox() {
		for (GeneralElement el : elements) {
			if (el instanceof Box) {
				return true;
			}
		}
		return false;
	}

	public boolean hasTarget() {
		for (GeneralElement el : elements) {
			if (el instanceof Target) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPlayer() {
		for (GeneralElement el : elements) {
			if (el instanceof Player) {
				return true;
			}
		}
		return false;
	}

	public Player getPlayer() {
		for (GeneralElement el : elements) {
			if (el instanceof Player) {
				return (Player) el;
			}
		}
		return null;
	}

	public Box getBox() {
		for (GeneralElement el : elements) {
			if (el instanceof Box) {
				return (Box) el;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		for (GeneralElement ge : this.elements) {
			build.append(ge.toString() + ",");
		}
		return build.toString();
	}

}
