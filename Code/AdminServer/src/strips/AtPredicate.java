package strips;

import model.data.gameElem.Position;
import predicates.Predicate;

public class AtPredicate implements Predicate {

	private Class<?> type;
	private Position position;

	public AtPredicate(Class<?> type, Position position) {
		this.type = type;
		this.position = position;
	}

	@Override
	public boolean equals(Object p) {
		if (p instanceof AtPredicate) {
			AtPredicate predicate = (AtPredicate) p;
			return predicate.position.equals(position) && predicate.type.equals(this.type);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Position: " + this.position.toString() + " Element Type: " + this.type.toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public Position getPosition() {
		return this.position;
	}

	public Class<?> getElementType() {
		return this.type;
	}

	@Override
	public boolean isSetisfied(Predicate predicate) {
		return equals(predicate);
	}


}
