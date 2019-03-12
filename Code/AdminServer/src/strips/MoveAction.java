package strips;

import java.util.HashMap;

import model.data.gameElem.*;
import plannable.Action;
import plannable.State;
import predicates.Predicate;

public class MoveAction extends Action {

	private static HashMap<String, MoveAction> moves = new HashMap<String, MoveAction>();

	public static MoveAction getInstance(String direction) {
		MoveAction action = moves.get(direction);
		if (action == null) {
			action = new MoveAction(direction);
			moves.put(direction, action);
		}
		return action;
	}

	private String direction;

	private MoveAction(String direction) {
		this.direction = direction;
	}

	@Override
	public void execute(State state) throws Exception {
		AtPredicate playerPred = null;
		//find the player position
		for (Predicate pred : state.getPredicate()) {
			if (pred instanceof AtPredicate && ((AtPredicate) pred).getElementType().equals(Player.class)) {
				playerPred = (AtPredicate) pred;
				break;
			}
		}
		if (playerPred == null)
			throw new Exception("Player predicate not found");
		
		state.removePredicate(playerPred);
		Position newPlayerPos;
		AtPredicate boxPred;
		switch (this.direction) {
		case "move right":
			newPlayerPos = new Position(playerPred.getPosition().getX() + 1, playerPred.getPosition().getY());
			state.addPredicate(new AtPredicate(Player.class, newPlayerPos));
			boxPred = new AtPredicate(Box.class, newPlayerPos);
			if (state.isSatisfied(boxPred)) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(Box.class,
						new Position(newPlayerPos.getX() + 1, newPlayerPos.getY())));
			}
			break;
		case "move left":
			newPlayerPos = new Position(playerPred.getPosition().getX() - 1, playerPred.getPosition().getY());
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = new AtPredicate(Box.class, newPlayerPos);
			if (state.isSatisfied(boxPred)) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(Box.class,
						new Position(newPlayerPos.getX() - 1, newPlayerPos.getY())));
			}
			break;
		case "move up":
			newPlayerPos = new Position(playerPred.getPosition().getX(), playerPred.getPosition().getY() - 1);
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = new AtPredicate(Box.class, newPlayerPos);
			if (state.isSatisfied(boxPred)) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(Box.class,
						new Position(newPlayerPos.getX(), newPlayerPos.getY() - 1)));
			}
			break;
		case "move down":
			newPlayerPos = new Position(playerPred.getPosition().getX(), playerPred.getPosition().getY() + 1);
			state.addPredicate(new AtPredicate(playerPred.getElementType(), newPlayerPos));
			boxPred = new AtPredicate(Box.class, newPlayerPos);
			if (state.isSatisfied(boxPred)) {
				state.removePredicate(boxPred);
				state.addPredicate(new AtPredicate(Box.class,
						new Position(newPlayerPos.getX(), newPlayerPos.getY() + 1)));
			}
			break;
		default:
			System.out.println("Unknow action to execute");
			break;
		}
	}


	@Override
	public String toString(){
		return this.direction;
	}

}
