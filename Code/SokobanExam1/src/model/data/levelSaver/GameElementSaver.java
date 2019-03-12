package model.data.levelSaver;

import java.util.ArrayList;
import java.util.HashMap;

import model.data.gameElem.Cell;
import model.data.gameElem.GeneralElement;

public class GameElementSaver {

	private HashMap<String, Character> hm;

	public GameElementSaver() {
		hm = new HashMap<String, Character>();
		hm.put("Wall", '#');
		hm.put("WallPlayer", '&');
		hm.put("PlayerWall", '&');
		hm.put("WallBox", '?');
		hm.put("BoxWall", '?');
		hm.put("Floor", ' ');
		hm.put("FloorBox", '@');
		hm.put("BoxFloor", '@');
		hm.put("FloorPlayer", 'A');
		hm.put("PlayerFloor", 'A');
		hm.put("Target", 'o');
		hm.put("TargetPlayer", 'a');
		hm.put("PlayerTarget", 'a');
		hm.put("TargetBox", '*');
		hm.put("BoxTarget", '*');
		hm.put("",'\t');
	}

	public char getChar(Cell cell) {
		String concatName = "";
		ArrayList<GeneralElement> list = cell.getElements();
		for (int i = 0; i < list.size(); i++) {
			concatName += getClassName(list.get(i));
		}
		return hm.get(concatName);
	}

	private String getClassName(Object obj) {
		String className="";
		try{
		 className= obj.getClass().getName();
		}catch(Exception e)
		{
		e.printStackTrace();
		}
		return className.substring(className.lastIndexOf('.') + 1);
	}
}