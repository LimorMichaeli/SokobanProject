package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import common.LevelData;
import db.IScoresDataMapper;
import db.Score;
import db.ScoresQuery;
import model.data.gameElem.Cell;
import model.data.level.Level;
import model.data.levelLoader.ILevelLoader;
import model.data.levelLoader.LoadFileTypeFactory;
import model.data.levelSaver.ILevelSaver;
import model.data.levelSaver.SaveFileTypeFactory;
import model.data.policy.IPolicy;
import model.data.policy.MySokobanPolicy;
import model.serverProtocol.SolutionRespone;
import model.serverProtocol.SolutionsGateway;

public class MyModel extends Observable implements IModel {

	private Level level;
	private IPolicy policy;
	private Timer timer;
	private IScoresDataMapper scoresDataMapper;
	private SolutionsGateway solutionsGateway;

	public MyModel(IScoresDataMapper scoreDM, SolutionsGateway solutionsGateway) {
		this.policy = new MySokobanPolicy();
		this.scoresDataMapper = scoreDM;
		this.solutionsGateway=solutionsGateway;
	}

	@Override
	public void movePlayer(String direction) {
		if (!policy.isWin(level) && policy.move(level, direction)) {
			int steps = level.getNumOfSteps();
			steps++;
			level.setNumOfSteps(steps);
			setChanged();
			LinkedList<Object> params = new LinkedList<Object>();
			params.add("Display");
			params.add(steps);
			notifyObservers(params);
			if (policy.isWin(level)) {
				timer.cancel();
				setChanged();
				LinkedList<Object> winParams = new LinkedList<Object>();
				winParams.add("Win");
				notifyObservers(winParams);
			}
		}
	}

	@Override
	public void loadLevel(String path) {
		LoadFileTypeFactory lf = new LoadFileTypeFactory();
		ILevelLoader ill = lf.createLoader(path);

		try {
			level = ill.loadLevel(new FileInputStream(path));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setChanged();
		LinkedList<Object> oparams = new LinkedList<Object>();
		oparams.add("Display");
		oparams.add(0);
		notifyObservers(oparams);
		startTimer();
	}

	@Override
	public void saveLevel(String path) {
		SaveFileTypeFactory lf = new SaveFileTypeFactory();
		ILevelSaver ils = lf.CreateSaver(path);
		try {
			ils.saveLevel(new FileOutputStream(path), level);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setChanged();
		LinkedList<String> oparams = new LinkedList<String>();
		oparams.add("Display");
		notifyObservers(oparams);

	}

	@Override
	public LevelData getCurrentLevel() {
		Cell[][] board = level.getBoard();
		char[][] symbolBoard = new char[board.length][];

		for (int i = 0; i < board.length; i++) {
			symbolBoard[i] = new char[board[i].length];

			for (int j = 0; j < board[i].length; j++)
				symbolBoard[i][j] = board[i][j].getSymbol();
		}

		return new LevelData(symbolBoard, level.getNumOfSteps(), level.getName());
	}

	private void startTimer() {
		if (timer != null)
			timer.cancel();

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				level.setCountSeconds(level.getCountSeconds() + 1);
				setChanged();
				ArrayList<Object> params = new ArrayList<>();
				params.add("UpdateSeconds");
				params.add(level.getCountSeconds());
				notifyObservers(params);
			}
		}, 0, 1000);
	}

	@Override
	public void saveScore(String userName) {
		scoresDataMapper.save(new Score(level.getName(), level.getNumOfSteps(), level.getCountSeconds(), userName));
	}

	@Override
	public void searchScores(ScoresQuery query) {
		Score[] scores = scoresDataMapper.search(query);
		setChanged();
		ArrayList<Object> params = new ArrayList<>();
		params.add("ResponeScores");
		params.add(scores);
		notifyObservers(params);
	}
	

	@Override
	public SolutionRespone getSolution() {
		if (this.level == null)
			return null;

		return this.solutionsGateway.GetSolution(this.level);
	}

}