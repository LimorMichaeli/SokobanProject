package db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Scores")
public class Score implements Serializable{
	
	private static final long serialVersionUID = -3317467188102121194L;

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto generated, do
														// not try to generate
														// by yourself
	private int id;

	@Column(name = "LevelName")
	private String levelName;

	@Column(name = "Steps")
	private int steps;

	@Column(name = "Seconds")
	private int seconds;

	@Column(name = "UserName")
	private String userName;

	public Score() {
		// TODO Auto-generated constructor stub
	}

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
