package model.data.levelLoader;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.InputStream;

import model.data.level.Level;




public class MyXMLLevelLoader implements ILevelLoader{

	@Override
	public Level loadLevel(InputStream input) {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(input));
		Level level = (Level)decoder.readObject();
	
		decoder.close();
	
	return level;
	}

}
