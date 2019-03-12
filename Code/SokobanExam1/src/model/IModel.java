package model;

import common.LevelData;
import db.ScoresQuery;
import model.serverProtocol.SolutionRespone;

public interface IModel {
	
	public void movePlayer(String direction);
	
	public void loadLevel(String path);
	
	public void saveLevel(String path);
	
	public LevelData getCurrentLevel();
	
	public void saveScore(String userName);
	
	public void searchScores(ScoresQuery query);

	SolutionRespone getSolution();
}
