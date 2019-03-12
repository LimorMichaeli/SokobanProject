package searcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Action;
import model.Searchable;
import model.State;

public class BFS<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<Action<?>> search(Searchable<T> searchable) {
		HashSet<State<T>> close = new HashSet<State<T>>();  //states we've already visited
		State<T> currentState; 
		initQueue(searchable.getInitialState());
		while ((currentState = poolQueue()) != null) {
			increaseEvaluatedNodes();
			close.add(currentState);
			if (searchable.isGoalSatisfied(currentState)) {
				return backTrace(currentState);
			}
			List<State<T>> successors = searchable.getAllPossibleStates(currentState);
			for (State<T> s : successors) {
				if (close.contains(s) == false) {
					State<T> duplicateState;
					if ((duplicateState = getStateFromQueue(s)) == null) {
						pushToQueue(s);
					} else {
						if (duplicateState.getCost() > s.getCost()) {
							replaceStates(duplicateState, s);
						}
					}
				}
			}
		}
		return null;
	}
}
