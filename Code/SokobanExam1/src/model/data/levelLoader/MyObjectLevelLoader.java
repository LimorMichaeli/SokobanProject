package model.data.levelLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import model.data.level.Level;


public class MyObjectLevelLoader implements ILevelLoader {

	@Override
	public Level loadLevel(InputStream input) throws IOException {

		Level level = null;
		ObjectInputStream ois = new ObjectInputStream(input);
		try {
			level = (Level) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
	}
}
