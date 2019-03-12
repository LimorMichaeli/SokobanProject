package model.data.gameElem;

public class Floor extends GeneralElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Floor() {}
	
	public Floor(Position p){
		super.setPos(p);
	}
	
	@Override
	public String toString() {
		return "Floor " + this.getPos().toString();
	}
}
