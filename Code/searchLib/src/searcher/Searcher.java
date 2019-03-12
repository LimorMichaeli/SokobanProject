package searcher;

import java.util.ArrayList;

import model.Action;
import model.Searchable;

public interface Searcher<T> {
	
	public ArrayList<Action<?>> search (Searchable<T> searchable);
	public int getNumberOfNodesEvaluated();
	
}
