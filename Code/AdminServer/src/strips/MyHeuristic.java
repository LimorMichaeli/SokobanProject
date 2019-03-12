package strips;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.data.gameElem.*;
import model.data.level.*;
import model.data.policy.IPolicy;
import plannable.Action;
import plannable.GoalState;
import plannable.State;
import planner.CantSolvedException;
import predicates.Predicate;
import searcher.BoardSearchable;
import searcher.BoardState;
import searcher.SearcherCallable;

public class MyHeuristic implements planner.Heuristic {

	private Level level;
	private IPolicy policy;

	public MyHeuristic(Level level, IPolicy policy) {
		this.level = level;
		this.policy = policy;
	}

	@Override
	public List<Predicate> decomposeGoal(GoalState g) {
		ArrayList<Predicate> decom = new ArrayList<>();
		decom.addAll(g.getPredicate());
		return decom;
	}

	@Override
	public List<Action> getSatisfiedActions(Predicate predicate, State state) throws CantSolvedException {
		if (predicate instanceof AtPredicate) {
			AtPredicate pred = (AtPredicate) predicate;
			if (pred.getElementType().equals(Box.class)) {
				
				List<SearcherCallable> BFSCallableList = getCallableSearchables(pred.getPosition(), state);
				ExecutorService threadPool = Executors.newFixedThreadPool(5);
				List<Future<List<model.Action<?>>>> futureResults;
				try {
					futureResults = threadPool.invokeAll(BFSCallableList);
					List<model.Action<?>> minActions = null;
					for (Future<List<model.Action<?>>> future : futureResults) {
						List<model.Action<?>> actions = future.get();
						if (actions != null && (minActions == null || actions.size() < minActions.size()))
							minActions = actions;
					}
					if (minActions != null) {
						ArrayList<Action> actions = new ArrayList<>();
						for (model.Action<?> ac : minActions)
							actions.add(MoveAction.getInstance(ac.getAction().toString()));
						return actions;
					}
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					throw new CantSolvedException();
				}
			}
		}
		throw new CantSolvedException();
	}

	private List<SearcherCallable> getCallableSearchables(Position targetPostion, State state) {
		ArrayList<SearcherCallable> searchableList = new ArrayList<>();
		for (Predicate p : state.getPredicate()) {
			if (p instanceof AtPredicate && ((AtPredicate) p).getElementType().equals(Box.class)) {
				AtPredicate boxP = (AtPredicate) p;

				if (state.getPredicate().contains(new AtPredicate(Target.class, boxP.getPosition())) == false) {
					Class<?>[][] stateBoard = new Class[this.level.getBoard().length][];
					for (int i = 0; i < stateBoard.length; i++)
						stateBoard[i] = new Class<?>[this.level.getBoard()[i].length];
					for (Predicate pred : state.getPredicate()) {
						if (pred instanceof AtPredicate) {
							AtPredicate atPredicate = (AtPredicate) pred;
							Class<?> type = atPredicate.getElementType();

							if (type.equals(Box.class) || type.equals(Wall.class)) {
								stateBoard[atPredicate.getPosition().getY()][atPredicate.getPosition()
										.getX()] = Wall.class;
							} else {
								if (Target.class.equals(type) == false)
									stateBoard[atPredicate.getPosition().getY()][atPredicate.getPosition().getX()] = type
											.equals(Player.class) ? Player.class : null;
							}
						}
					}
					BoardSearchable searchable = new BoardSearchable(this.policy);

					stateBoard[boxP.getPosition().getY()][boxP.getPosition().getX()] = Box.class;
					searchable.setInitialState(new BoardState(stateBoard));

					Class<?>[][] goalBoard = new Class[stateBoard.length][];
					for (int i = 0; i < goalBoard.length; i++)
						goalBoard[i] = stateBoard[i].clone();
					goalBoard[boxP.getPosition().getY()][boxP.getPosition().getX()] = null;
					goalBoard[targetPostion.getY()][targetPostion.getX()] = Box.class;
					searchable.setGoalState(new BoardState(goalBoard));
					searchableList.add(new SearcherCallable(searchable));
				}
			}
		}
		return searchableList;
	}

}
