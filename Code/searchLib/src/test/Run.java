package test;

import java.util.ArrayList;
import model.Action;
import searcher.BFS;



public class Run {

	public static void main(String[] args) {
		int[][] board = { { 1, 2, 3 }, { 4, 0, 5 }, { 6, 7, 8 } };

		EightPuzzle game = new EightPuzzle(board);
		System.out.println(game.toString());
		EightPuzzleAdapter adapter = new EightPuzzleAdapter(game);
		BFS<EightPuzzleState> bfs = new BFS<>();
		ArrayList<Action<?>> results = bfs.search(adapter);
		if (results != null) {
			System.out.println("The puzzle solved, The actions that need to be made are:\n");
			for (Action<?>action : results) {
				System.out.println(action.toString());
			}
		} else {
			System.out.println("The puzzle can't be solved");
		}
	}

}
