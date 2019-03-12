package test;

import java.util.ArrayList;
import java.util.List;

import model.Action;
import model.Searchable;
import model.State;


public class EightPuzzleAdapter implements Searchable<EightPuzzleState> {

	private EightPuzzle game;
	private State<EightPuzzleState> initState;
	private State<EightPuzzleState> goalState;

	public EightPuzzleAdapter(EightPuzzle game) {
		this.game = game;
	}

	@Override
	public State<EightPuzzleState> getInitialState() {
		if (this.initState == null) {
			int[][] board = game.getBoard();
			EightPuzzleState s = new EightPuzzleState(board);
			this.initState = new State<EightPuzzleState>(s);
		}
		return this.initState;
	}

	@Override
	public State<EightPuzzleState> getGoalState() {
		if (this.goalState == null) {
			int[][] board = new int[game.getBoard().length][game.getBoard().length];
			int count = 0;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					board[i][j] = count++;
				}
			}

			EightPuzzleState s = new EightPuzzleState(board);
			this.goalState = new State<EightPuzzleState>(s);
		}
		return this.goalState;
	}

	private int[][] copyBoard(int[][] board) {
		int[][] newBoard = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}

	@Override
	public List<State<EightPuzzleState>> getAllPossibleStates(State<EightPuzzleState> state) {

		int[][] board = state.getState().numbers;
		Position pos = game.getEmptyCell(board);
		ArrayList<State<EightPuzzleState>> list = new ArrayList<>();

		if (pos.getY() > 0) {
			Action<String> action = new Action<String>("left");
			int[][] newBoard = copyBoard(board);
			newBoard[pos.getX()][pos.getY()] = board[pos.getX()][pos.getY() - 1];
			newBoard[pos.getX()][pos.getY() - 1] = board[pos.getX()][pos.getY()];

			EightPuzzleState s = new EightPuzzleState(newBoard);
			list.add(new State<EightPuzzleState>(s, state, state.getCost() + 1, action));
		}
		if (pos.getY() < board.length - 1) { // right
			Action<String> action = new Action<String>("right");

			int[][] newBoard = copyBoard(board);
			newBoard[pos.getX()][pos.getY()] = board[pos.getX()][pos.getY() + 1];
			newBoard[pos.getX()][pos.getY() + 1] = board[pos.getX()][pos.getY()];

			EightPuzzleState s = new EightPuzzleState(newBoard);
			list.add(new State<EightPuzzleState>(s, state, state.getCost() + 1, action));
		}

		if (pos.getX() < board.length - 1) { // move empty space down
			Action<String> action = new Action<String>("down");

			int[][] newBoard = copyBoard(board);
			newBoard[pos.getX()][pos.getY()] = board[pos.getX() + 1][pos.getY()];
			newBoard[pos.getX() + 1][pos.getY()] = board[pos.getX()][pos.getY()];

			EightPuzzleState s = new EightPuzzleState(newBoard);
			list.add(new State<EightPuzzleState>(s, state, state.getCost() + 1, action));
		}
		if (pos.getX() > 0) { // move empty space up
			Action<String> action = new Action<String>("up");

			int[][] newBoard = copyBoard(board);
			newBoard[pos.getX()][pos.getY()] = board[pos.getX() - 1][pos.getY()];
			newBoard[pos.getX() - 1][pos.getY()] = board[pos.getX()][pos.getY()];

			EightPuzzleState s = new EightPuzzleState(newBoard);
			list.add(new State<EightPuzzleState>(s, state, state.getCost() + 1, action));
		}

		return list;
	}

	@Override
	public boolean isGoalSatisfied(State<EightPuzzleState> state) {
		return getGoalState().equals(state);
	}

	@Override
	public List<State<EightPuzzleState>> getAllStates() {
		return null;
	}

}
