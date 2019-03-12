package model.data.gameElem;

public class Wall extends GeneralElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Wall() {}
	
	public Wall(Position p){
		super.setPos(p);
	}

	@Override
	public String toString() {
		return "Wall " + this.getPos().toString();
	}
}
