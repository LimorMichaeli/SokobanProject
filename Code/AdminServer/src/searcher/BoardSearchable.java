package searcher;

import java.util.ArrayList;
import java.util.List;

import model.Action;
import model.Searchable;
import model.State;
import model.data.gameElem.Box;
import model.data.policy.IPolicy;

public class BoardSearchable implements Searchable<BoardState> {

	private BoardState initState;
	private BoardState goalState;
	private IPolicy policy;

	public BoardSearchable(IPolicy policy) {
		this.policy = policy;
	}

	@Override
	public State<BoardState> getInitialState() {
		return new State<BoardState>(this.initState);
	}

	public void setInitialState(BoardState initState) {
		this.initState = initState;
	}

	public void setGoalState(BoardState goalState) {
		this.goalState = goalState;
	}

	@Override
	public State<BoardState> getGoalState() {
		return new State<BoardState>(this.goalState);
	}

	@Override
	public List<State<BoardState>> getAllPossibleStates(State<BoardState> state) {
		ArrayList<State<BoardState>> possibleState = new ArrayList<State<BoardState>>();
		Class<?>[][] board = state.getState().getBoard();
		Class<?>[][] rightBoard = cloneMatrix(board), leftBoard = cloneMatrix(board), upBoard = cloneMatrix(board),
				downBoard = cloneMatrix(board);
		if (this.policy.move(rightBoard, "right")) {
			possibleState.add(new State<BoardState>(new BoardState(rightBoard), state, state.getCost() + 1,
					new Action<String>("move right")));
		}
		if (this.policy.move(leftBoard, "left")) {
			possibleState.add(new State<BoardState>(new BoardState(leftBoard), state, state.getCost() + 1,
					new Action<String>("move left")));
		}
		if (this.policy.move(upBoard, "up")) {
			possibleState.add(new State<BoardState>(new BoardState(upBoard), state, state.getCost() + 1,
					new Action<String>("move up")));
		}
		if (this.policy.move(downBoard, "down")) {
			possibleState.add(new State<BoardState>(new BoardState(downBoard), state, state.getCost() + 1,
					new Action<String>("move down")));
		}
		return possibleState;
	}

	private Class<?>[][] cloneMatrix(Class<?>[][] matrix) {
		Class<?>[][] clone = new Class<?>[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			clone[i] = matrix[i].clone();
		return clone;
	}

	@Override
	public boolean isGoalSatisfied(State<BoardState> state) {
		Class<?>[][] goalBoard = this.goalState.getBoard();
		Class<?>[][] stateBoard = state.getState().getBoard();
		for (int y = 0; y < goalBoard.length; y++)
			for (int x = 0; x < goalBoard[y].length; x++) {
				if (Box.class.equals(goalBoard[y][x]) && Box.class.equals(stateBoard[y][x]) == false)
					return false;
			}
		return true;
	}

	@Override
	public List<State<BoardState>> getAllStates() {
		return null;
	}

}
