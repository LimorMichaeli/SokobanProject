package model.data.levelSaver;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

import model.data.level.Level;


public class MyXMLLevelSaver implements ILevelSaver{

	@Override
	public void saveLevel(OutputStream output, Level level) {
		
		if (output != null && level != null) {

			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(output));
			encoder.writeObject(level);
			encoder.close();

		}
	
	}

}
