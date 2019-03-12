package model.data.levelSaver;

import java.io.IOException;
import java.io.OutputStream;

import model.data.level.Level;

public interface ILevelSaver  {
	public void saveLevel(OutputStream output, Level level) throws IOException;
}
