package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import common.LevelData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** A class to display the sokoban to the player */
public class SokobanDisplayer extends Canvas {

	// Variable declaration
	private LevelData levelData;
	private StringProperty wallFileName;
	private StringProperty boxFileName;
	private StringProperty playerFileName;
	private StringProperty backgroundFileName;
	private StringProperty targetFileName;

	private Image wallimg;
	private Image boximg;
	private Image playerimg;
	private Image backgroundimg;
	private Image targetimg;

	HashMap<Character, Image> hmap;

	/** @return The target file name */
	public String getTargetFileName() {
		return targetFileName.get();
	}

	/**
	 * @param targetFileName
	 *            The target file name to set
	 */
	public void setTargetFileName(String targetFileName) {
		this.targetFileName.set(targetFileName);

		try {
			targetimg = new Image(new FileInputStream(targetFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.out);
			System.exit(0);
		}
		hmap.put('o', targetimg);
	}

	/** @returns The background file name. */
	public String getBackgroundFileName() {
		return backgroundFileName.get();
	}

	/**
	 * @param backgroundFileName
	 *            Desired background file name to set.
	 */
	public void setBackgroundFileName(String backgroundFileName) {
		this.backgroundFileName.set(backgroundFileName);
		try {
			backgroundimg = new Image(new FileInputStream(backgroundFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.out);
			System.exit(0);
		}
	}

	/** @returns The player file name */
	public String getPlayerFileName() {
		return playerFileName.get();
	}

	/**
	 * @param playerFileName
	 *            The desired player file name to set.
	 */
	public void setPlayerFileName(String playerFileName) {
		this.playerFileName.set(playerFileName);
		try {
			playerimg = new Image(new FileInputStream(playerFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.out);
			System.exit(0);
		}
		hmap.put('A', playerimg);
		hmap.put('a', playerimg);
	}

	/** Setting the level data */
	public void setLevelData(LevelData levelData) {
		this.levelData = levelData;
	}

	/** Class constructor */
	public SokobanDisplayer() {
		wallFileName = new SimpleStringProperty();
		boxFileName = new SimpleStringProperty();
		playerFileName = new SimpleStringProperty();
		backgroundFileName = new SimpleStringProperty();
		targetFileName = new SimpleStringProperty();

		hmap = new HashMap<Character, Image>();
	}

	/** @return The box file name */
	public String getBoxFileName() {
		return boxFileName.get();
	}

	/** Setting the box file name */
	public void setBoxFileName(String boxFileName) {
		this.boxFileName.set(boxFileName);
		try {
			boximg = new Image(new FileInputStream(boxFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.out);
		}
		hmap.put('@', boximg);
		hmap.put('*', boximg);
	}

	/** @return The wall file name. */
	public String getWallFileName() {
		return wallFileName.get();
	}

	/**
	 * Setting the wall file name
	 * 
	 * @param wallFileName
	 *            Desired fire name.
	 */
	public void setWallFileName(String wallFileName) {
		this.wallFileName.set(wallFileName);
		try {
			wallimg = new Image(new FileInputStream(wallFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.out);
		}
		hmap.put('#', wallimg);
	}

	/** Drawing the level to the screen */
	public void redraw() {
		if (levelData != null) {

			char[][] map = levelData.getBoard();
			double W = getWidth();
			double H = getHeight();
			double w, h;

			w = W / (map[0].length);
			h = H / (map.length);

			GraphicsContext gc = getGraphicsContext2D();

			gc.clearRect(0, 0, W, H);

			gc.drawImage(backgroundimg, 0, 0, W, H);

			for (int i = 0; i < map.length; i++) {

				for (int j = 0; j < map[0].length; j++) {

					if (hmap.get(map[i][j]) != null) {
						Image objImg = hmap.get(map[i][j]);

						gc.drawImage(objImg, j * w, i * h, w, h);

					}
				}
			}
		}

	}

}
