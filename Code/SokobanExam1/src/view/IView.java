package view;

import java.util.LinkedList;

import common.LevelData;
import db.Score;

public interface IView {

	public void displayLevel(LevelData board, Integer steps);

	public void updateTime(int seconds);

	public void displayMessage(LinkedList<String> params);

	public void userWin();

	public void setScores(Score[] scores);

	void setSolution(String[] actions);
}
