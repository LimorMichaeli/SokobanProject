package model.data.policy;

import model.data.level.Level;

public interface IPolicy {

	public boolean move(Level level, String direction);

	public boolean isWin(Level level);
	
	public boolean move(Class<?>[][] board, String direction);
}
