package common;

public class LevelData {

	int numOfSteps;
	private String name;
	char[][] board;

	public LevelData(char[][] symbolBoard, int numOfSteps,String name) {
		this.name=name;
		board=symbolBoard;
		this.numOfSteps=numOfSteps;
		
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public int getNumOfSteps() {
		return numOfSteps;
	}

	public void setNumOfSteps(int numOfSteps) {
		this.numOfSteps = numOfSteps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
