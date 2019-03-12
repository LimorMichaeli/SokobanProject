package test;

public class EightPuzzleState {
	int[][] numbers;

	public EightPuzzleState(int[][] numbers) {
		this.numbers = numbers;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				str += numbers[i][j];
			}
			str += "\n";
		}
		return str;
	}

	@Override
	public int hashCode() {
		int hashCode = 0, count = 1;
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				hashCode += count * numbers[i][j];
				count *= 10;
			}
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		EightPuzzleState state = (EightPuzzleState) obj;
		return state.hashCode() == hashCode();
	}

}
