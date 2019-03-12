package model.data.gameElem;

public class Target extends GeneralElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Target() {}
	
	public Target(Position p){
		super.setPos(p);
	}
	
	@Override
	public String toString() {
		return "Target " + this.getPos().toString();
	}

}
