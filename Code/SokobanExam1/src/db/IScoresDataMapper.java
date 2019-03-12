package db;

public interface IScoresDataMapper { //open/close pattern
	
	public void save(Score score); //save score 
	
	public Score[] search(ScoresQuery query);
	
}
