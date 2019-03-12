package model.data.gameElem;

public class Box extends GeneralElement {

	private static final long serialVersionUID = 1L;

	public Box() {
	}

	public Box(Position p) {
		super.setPos(p);
	}

	@Override
	public String toString() {
		return "Box " + this.getPos().toString();
	}

}
