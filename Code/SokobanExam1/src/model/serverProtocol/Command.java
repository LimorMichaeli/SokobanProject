package model.serverProtocol;

import java.io.Serializable;

import db.Score;
import db.ScoresQuery;
import model.data.level.Level;

public class Command implements Serializable {

	private static final long serialVersionUID = 54658;

	private String name;

	private Level level;

	private Score score;
	
	private ScoresQuery query;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return this.level;
	}
	
	public Score getScore() {
		return this.score;
	}

	public void setScore(Score recored) {
		this.score = recored;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public ScoresQuery getQuery() {
		return this.query;
	}

	public void setQuery(ScoresQuery query) {
		this.query = query;
	}

	


}
