package model.data.levelSaver;

import java.util.HashMap;

public class SaveFileTypeFactory {

	private HashMap<String, ICreator> hm;

	private interface ICreator {

		public ILevelSaver create();
	}

	private class TextSaverCreator implements ICreator {

		@Override
		public MyTextLevelSaver create() {
			return new MyTextLevelSaver();
		}

	}

	private class XMLSaverCreator implements ICreator {

		@Override
		public MyXMLLevelSaver create() {
			return new MyXMLLevelSaver();
		}

	}

	private class ObjSaverCreator implements ICreator {

		@Override
		public MyObjectLevelSaver create() {
			return new MyObjectLevelSaver();
		}

	}

	public SaveFileTypeFactory() {

		hm = new HashMap<String, ICreator>();
		hm.put("txt", new TextSaverCreator());
		hm.put("xml", new XMLSaverCreator());
		hm.put("obj", new ObjSaverCreator());

	}

	public ILevelSaver CreateSaver(String path) {

		ICreator creator = hm.get(path.substring(path.lastIndexOf('.') + 1));
		if (creator == null)
			return null;
		return creator.create();

	}

}
