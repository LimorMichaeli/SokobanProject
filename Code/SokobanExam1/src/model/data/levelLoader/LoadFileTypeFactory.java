package model.data.levelLoader;

import java.util.HashMap;

public class LoadFileTypeFactory {

	private HashMap<String, ICreator> hm;

	public LoadFileTypeFactory() {
		hm = new HashMap<String, ICreator>();
		hm.put("txt", new TxtLevelCreator());
		hm.put("xml", new XmlLevelCreator());
		hm.put("obj", new ObjLevelCreator());
	}

	private interface ICreator {

		ILevelLoader createLevelLoader();
	}

	private class TxtLevelCreator implements ICreator {
		@Override
		public ILevelLoader createLevelLoader() {
			return new MyTextLevelLoader();
		}
	}

	private class ObjLevelCreator implements ICreator {
		@Override
		public ILevelLoader createLevelLoader() {
			return new MyObjectLevelLoader();
		}
	}

	private class XmlLevelCreator implements ICreator {
		@Override
		public ILevelLoader createLevelLoader() {
			return new MyXMLLevelLoader();
		}
	}

	public ILevelLoader createLoader(String path) {
		ICreator creator = hm.get(path.substring(path.lastIndexOf('.') + 1));
		if (creator == null)
			return null;
		return creator.createLevelLoader();
	}
}
