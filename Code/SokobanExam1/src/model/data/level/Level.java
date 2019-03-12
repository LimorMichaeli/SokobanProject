package model.data.level;

import java.io.Serializable;

import model.data.gameElem.Cell;
import model.data.gameElem.Position;

public class Level implements Serializable{



	/**
	 * 
	 */
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

}
