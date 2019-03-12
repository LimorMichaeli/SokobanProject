package view;

import java.io.IOException;
import java.io.OutputStream;

import common.LevelData;

public interface Displayer {
	
	public void display(LevelData levelData, OutputStream os) throws IOException;

}
