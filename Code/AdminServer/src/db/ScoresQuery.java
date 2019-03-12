package db;

import java.io.Serializable;

public class ScoresQuery implements Serializable{

	public String userName;

	public String levelName;

	public String orderBy;

	public int page;

	public ScoresQuery() {
		page = 0;
		orderBy = "seconds";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
