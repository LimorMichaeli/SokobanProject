package model.data.levelLoader;

import java.io.IOException;
import java.io.InputStream;

import model.data.level.Level;


public interface ILevelLoader {
	public Level loadLevel(InputStream input) throws IOException, Exception;

}
