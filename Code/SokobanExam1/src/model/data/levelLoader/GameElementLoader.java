package model.data.levelLoader;

import java.util.HashMap;

import model.data.gameElem.Box;
import model.data.gameElem.Floor;
import model.data.gameElem.GeneralElement;
import model.data.gameElem.Player;
import model.data.gameElem.Target;
import model.data.gameElem.Wall;

public class GameElementLoader {

	private HashMap<Character, ICreator[]> hm;

	private interface ICreator {
		public GeneralElement create();
	}

	private class BoxCreator implements ICreator {
		@Override
		public GeneralElement create() {
			return new Box();
		}
	}

	private class PlayerCreator implements ICreator {
		@Override
		public GeneralElement create() {
			return new Player();
		}
	}

	private class TargetCreator implements ICreator {
		@Override
		public GeneralElement create() {
			return new Target();
		}

	}

	private class WallCreator implements ICreator {
		@Override
		public GeneralElement create() {
			return new Wall();
		}
	}

	private class FloorCreator implements ICreator {
		@Override
		public GeneralElement create() {
			return new Floor();
		}
	}

	public GameElementLoader() {
		hm = new HashMap<Character, ICreator[]>();
		hm.put('#', new ICreator[] { new WallCreator() });
		hm.put('&', new ICreator[] { new WallCreator(), new PlayerCreator() });
		hm.put('?', new ICreator[] { new WallCreator(), new BoxCreator() });
		hm.put('@', new ICreator[] { new FloorCreator(), new BoxCreator() });
		hm.put('A', new ICreator[] { new FloorCreator(), new PlayerCreator() });
		hm.put(' ', new ICreator[] { new FloorCreator() });
		hm.put('o', new ICreator[] { new TargetCreator() });
		hm.put('a', new ICreator[] { new TargetCreator(), new PlayerCreator() });
		hm.put('*', new ICreator[] { new TargetCreator(), new BoxCreator() });
		hm.put('\t', new ICreator[0]);
	}

	public GeneralElement[] CreateElements(char c) {
		ICreator[] cr = hm.get(c);
	
		GeneralElement[] arr = new GeneralElement[cr.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = cr[i].create();
		return arr;
	}

}
