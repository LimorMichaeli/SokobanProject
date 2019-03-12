package db;

import java.io.Serializable;

public class Score implements Serializable{
	
	private static final long serialVersionUID = -3317467188102121194L;

	private int id;

	private String levelName;

	private int steps;

	private int seconds;

	private String userName;

	public Score(String levelName, int steps, int seconds, String userName) {
		this.setLevelName(levelName);
		this.steps = steps;
		this.seconds = seconds;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return seconds / 60 + ":" + seconds % 60;
	}
}
