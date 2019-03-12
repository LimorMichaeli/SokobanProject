package test;

public class EightPuzzle {

	private int[][] board;

	public EightPuzzle(){
		
	}
	
	public EightPuzzle(int[][] board){
		setBoard(board);
	}
	
	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				str += board[i][j];
			}
			str += "\n";
		}
		return str;
	}

	public Position getEmptyCell(int[][] board) {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				if (board[i][j] == 0) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}
}
