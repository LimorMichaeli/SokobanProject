
package model.data.policy;

import model.data.gameElem.Box;
import model.data.gameElem.Cell;
import model.data.gameElem.Player;
import model.data.gameElem.Position;
import model.data.level.Level;

public class MySokobanPolicy implements IPolicy {

	public boolean move(Level level, String direction) {
		Position pos = level.getPlayerPos();

		switch (direction.toLowerCase()) {
		case "up":
			return moveInDirection(level, pos, new Position(pos.getX(), pos.getY() - 1),
					new Position(pos.getX(), pos.getY() - 2));
		case "down":
			return moveInDirection(level, pos, new Position(pos.getX(), pos.getY() + 1),
					new Position(pos.getX(), pos.getY() + 2));
		case "left":
			return moveInDirection(level, pos, new Position(pos.getX() - 1, pos.getY()),
					new Position(pos.getX() - 2, pos.getY()));
		case "right":
			return moveInDirection(level, pos, new Position(pos.getX() + 1, pos.getY()),
					new Position(pos.getX() + 2, pos.getY()));
		default:
			return false;
		}

	}

	public boolean isWin(Level level) {
		for (Cell[] row : level.getBoard())
			for (Cell cell : row) {
				if (cell.hasBox() != cell.hasTarget())
					return false;
			}
		return true;
	}

	private boolean moveInDirection(Level level, Position currPlayerPos, Position nextPlayerPos, Position nextBoxPos) {

		Cell[][] board = level.getBoard();
		Cell currCell = board[currPlayerPos.getY()][currPlayerPos.getX()];
		Cell nextCell = board[nextPlayerPos.getY()][nextPlayerPos.getX()];

		if (nextCell.hasWall())
			return false;
		else if (!(nextCell.hasBox())) {
			Player player = currCell.getPlayer();
			if (currCell.getSymbol() == 'a')
				currCell.setSymbol('o');
			else
				currCell.setSymbol(' ');
			currCell.getElements().remove(player);

			nextCell.getElements().add(player);
			if (nextCell.getSymbol() == 'o')
				nextCell.setSymbol('a');
			else
				nextCell.setSymbol('A');
			level.setPlayerPos(nextPlayerPos);
			return true;
		}

		else {
			Cell nextBoxCell = board[nextBoxPos.getY()][nextBoxPos.getX()];
			if (nextBoxCell.hasBox())
				return false;
			else if (nextBoxCell.hasWall())
				return false;

			else {
				Player player = currCell.getPlayer();
				if (currCell.getSymbol() == 'a')
					currCell.setSymbol('o');
				else
					currCell.setSymbol(' ');
				currCell.getElements().remove(player);
				nextCell.getElements().add(player);
				if (nextCell.getSymbol() == 'o' || nextCell.getSymbol() == '*')
					nextCell.setSymbol('a');
				else
					nextCell.setSymbol('A');

				level.setPlayerPos(nextPlayerPos);
				Box box = nextCell.getBox();
				nextCell.getElements().remove(box);
				nextBoxCell.getElements().add(box);
				if (nextBoxCell.symbol == 'o')
					nextBoxCell.setSymbol('*');
				else
					nextBoxCell.setSymbol('@');
				return true;
			}
		}
	}

}
