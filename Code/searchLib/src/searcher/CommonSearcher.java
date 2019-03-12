package searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import model.Action;
import model.State;

public abstract class CommonSearcher<T> implements Searcher<T> {

	private PriorityQueue<State<T>> openQueue;
	private int evaluatedNodes; // counter

	public CommonSearcher() {
		openQueue = new PriorityQueue<>();
		evaluatedNodes = 0;
	}

	protected void initQueue(State<T> initState) {   //first time we use the alg
		this.openQueue.clear();
		this.openQueue.add(initState);
	}

	protected void initQueue(List<State<T>> initState) {
		this.openQueue.clear();
		this.openQueue.addAll(initState);
	}
	
	protected void pushToQueue(State<T> state) {  
		this.openQueue.add(state);
	}

	protected PriorityQueue<State<T>> getQueue() {
		return openQueue;
	}

	protected State<T> poolQueue() {
		return this.openQueue.poll();
	}

	protected State<T> getStateFromQueue(State<T> state) {  //return specific state 
		for (State<T> s : this.openQueue)
			if (s.equals(state)) {
				return s;
			}
		return null;
	}

	protected void replaceStates(State<T> removeState, State<T> newState) {  
		this.openQueue.remove(removeState);
		this.openQueue.add(newState);
	}

	protected void increaseEvaluatedNodes() {
		this.evaluatedNodes++;
	}

	protected ArrayList<Action<?>> backTrace(State<T> state) {     //return the path of the solution
		Stack<Action<?>> stack = new Stack<Action<?>>();
		while (state.getParent() != null) {
			stack.push(state.getAction());
			state = state.getParent();
		}
		ArrayList<Action<?>> list = new ArrayList<>();
		while (stack.isEmpty() == false)
			list.add(stack.pop());
		return list;
	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}
}
