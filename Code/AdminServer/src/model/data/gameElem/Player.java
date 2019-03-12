package model.data.gameElem;

public class Player extends GeneralElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Player() {}
	
	public Player(Position p){
		super.setPos(p);
	}

	@Override
	public String toString() {
		return "Player " + this.getPos().toString();
	}
	
}
