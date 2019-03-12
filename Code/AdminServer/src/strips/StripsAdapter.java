package strips;

import java.util.ArrayList;
import java.util.List;

import model.data.gameElem.*;
import model.data.level.Level;
import plannable.GoalState;
import plannable.Plannable;
import plannable.State;
import predicates.Predicate;

public class StripsAdapter implements Plannable {
	private Level level;

	public StripsAdapter(Level level) {
		this.level = level;
	}

	@Override
	public GoalState getGoal() {
		List<Target> targets = this.level.getAllTargets();
		ArrayList<Predicate> goalList = new ArrayList<Predicate>();
		for (Target t : targets) {
			goalList.add(new AtPredicate(Box.class, t.getPos()));
		}
		return new GoalState(goalList);
	}

	@Override
	public State getInitialState() {
		Position playerPos = this.level.getPlayerPos();
		List<Box> boxes = this.level.getAllBoxes();
		List<Wall> walls = this.level.getAllWalls();
		List<Target> targets = this.level.getAllTargets();
		State state = new State();
		state.addPredicate(new AtPredicate(Player.class, new Position(playerPos)));
		for (Box b : boxes) {
			state.addPredicate(new AtPredicate(Box.class, new Position(b.getPos())));
		}
		for (Target t : targets) {
			state.addPredicate(new AtPredicate(Target.class, new Position(t.getPos())));
		}
		for (Wall w : walls) {
			state.addPredicate(new AtPredicate(Wall.class, new Position(w.getPos())));
		}

		return state;
	}

}
