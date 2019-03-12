package searcher;

import java.util.List;
import java.util.concurrent.Callable;

import model.Action;

public class SearcherCallable implements Callable<List<model.Action<?>>> {
	private BoardSearchable boardSearchable;

	public SearcherCallable(BoardSearchable boardSearchable) {
		this.boardSearchable = boardSearchable;
	}

	@Override
	public List<Action<?>> call() {
		BFS<BoardState> bfs = new BFS<>();
		return bfs.search(this.boardSearchable);
	}

}
