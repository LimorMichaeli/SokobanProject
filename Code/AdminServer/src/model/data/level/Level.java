package model.data.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.data.gameElem.*;

public class Level implements Serializable {

	private static final long serialVersionUID = 1L;

	public Level() {
		// TODO Auto-generated constructor stub
	}

	private Cell[][] board;
	private Position playerPos;
	private int boxNum;
	private int targrtNum;
	private int boxOnTarget;
	private String name;

	private int numOfSteps;
	private int countSeconds;

	public Cell[][] getBoard() {
		return board;
	}

	public void setBoard(Cell[][] board2) {
		this.board = board2;
	}

	public int getNumOfSteps() {
		return numOfSteps;
	}

	public void setNumOfSteps(int numOfSteps) {
		this.numOfSteps = numOfSteps;
	}

	public int getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(int boxNum) {
		this.boxNum = boxNum;
	}

	public int getTargrtNum() {
		return targrtNum;
	}

	public void setTargrtNum(int targrtNum) {
		this.targrtNum = targrtNum;
	}

	public int getBoxOnTarget() {
		return boxOnTarget;
	}

	public void setBoxOnTarget(int boxOnTarget) {
		this.boxOnTarget = boxOnTarget;
	}

	public Position getPlayerPos() {
		return playerPos;
	}

	public void setPlayerPos(Position pos) {
		this.playerPos = pos;
	}

	public int getCountSeconds() {
		return countSeconds;
	}

	public void setCountSeconds(int countSeconds) {
		this.countSeconds = countSeconds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Target> getAllTargets() {
		ArrayList<Target> results = new ArrayList<>();
		for (Cell[] row : this.board) {
			for (Cell cell : row) {
				for (GeneralElement ge : cell.getElements())
					if (ge instanceof Target) {
						results.add((Target) ge);
						break;
					}
			}
		}
		return results;
	}

	public List<Box> getAllBoxes() {
		ArrayList<Box> results = new ArrayList<>();
		for (Cell[] row : this.board) {
			for (Cell cell : row) {
				for (GeneralElement ge : cell.getElements())
					if (ge instanceof Box) {
						results.add((Box) ge);
						break;
					}
			}
		}
		return results;
	}

	public List<Wall> getAllWalls() {
		ArrayList<Wall> results = new ArrayList<>();
		for (Cell[] row : this.board) {
			for (Cell cell : row) {
				for (GeneralElement ge : cell.getElements())
					if (ge instanceof Wall) {
						results.add((Wall) ge);
						break;
					}
			}
		}
		return results;
	}
	
	@Override
	public String toString(){
		StringBuilder build=new StringBuilder();
		for(Cell[] row:this.board)
		{
			build.append("\n");
			for(Cell c:row){
				build.append("Cell{ ");
				build.append(c.toString());
				build.append(" } ");
			}
		}
		return build.toString();
	}

}
