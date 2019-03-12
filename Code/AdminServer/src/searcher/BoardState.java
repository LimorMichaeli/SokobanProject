package searcher;

public class BoardState {
	private Class<?>[][] board;
	private Integer hashCode = null;

	public BoardState(Class<?>[][] board) {
		this.board = board;
	}

	public Class<?>[][] getBoard() {
		return board;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Class<?>[] row : board) {
			builder.append("\n");
			for (Class<?> ty : row)
				builder.append(ty != null ? ty.toString() : "Null");
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		if (hashCode == null)
			hashCode = toString().hashCode();
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BoardState && ((BoardState) obj).hashCode() == hashCode();
	}
}
