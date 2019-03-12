package model.data.levelSaver;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import model.data.gameElem.Cell;
import model.data.level.Level;

public class MyTextLevelSaver implements ILevelSaver {
	@Override
	public void saveLevel(OutputStream output, Level level) {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		GameElementSaver factory = new GameElementSaver();
		Cell[][] board = level.getBoard();
		try {
			writer.write(level.getName());
			writer.newLine();
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					writer.write(factory.getChar(board[i][j]));
					writer.flush();
				}
				writer.newLine();
			}
			if (!(output instanceof PrintStream))
				writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
