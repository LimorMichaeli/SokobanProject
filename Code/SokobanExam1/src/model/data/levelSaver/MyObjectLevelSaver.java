package model.data.levelSaver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import model.data.level.Level;

public class MyObjectLevelSaver implements ILevelSaver {

	@Override
	public void saveLevel(OutputStream output, Level level) throws IOException {
		if (output != null && level != null) {

			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(output));
			
			oos.writeObject(level);
			oos.close();
	}

	}}
