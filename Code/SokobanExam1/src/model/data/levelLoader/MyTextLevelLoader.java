package model.data.levelLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.data.gameElem.Cell;
import model.data.gameElem.GeneralElement;
import model.data.gameElem.Player;
import model.data.gameElem.Position;
import model.data.level.Level;

public class MyTextLevelLoader implements ILevelLoader {

	@Override
	public Level loadLevel(InputStream input) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		GameElementLoader gel = new GameElementLoader();
		Level level = new Level();

		try {

			int rows = 0;
			int maxCol = 0;
			ArrayList<String> lines = new ArrayList<String>();
			String line;
			level.setName(reader.readLine());
			while ((line = reader.readLine()) != null) { // move line by line
															// until the end of
															// the file
				rows++;
				lines.add(line);
				if (line.length() > maxCol)
					maxCol = line.length();
			}

			Cell[][] board = new Cell[rows][maxCol];
			level.setBoard(board);

			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < maxCol; col++) {// move for each char in
														// the line
					line = lines.get(row);
					ArrayList<GeneralElement> elements = new ArrayList<GeneralElement>();
					board[row][col] = new Cell();
					if (col < line.length()) {
						char ch = line.charAt(col);
						board[row][col].setSymbol(ch);
						GeneralElement[] elems = gel.CreateElements(ch);

						for (GeneralElement e : elems) {
							Position pos=new Position(col, row);
							if (e instanceof Player)
								level.setPlayerPos(pos);
							e.setPos(pos);
							elements.add(e);
						}
					}
					board[row][col].setElements(elements);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return level;
	}

}
