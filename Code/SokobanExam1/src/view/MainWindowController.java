package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import common.LevelData;
import db.Score;
import db.ScoresQuery;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** The class that controlles the main window of the game */
public class MainWindowController extends Observable implements IView, Initializable, Observer {

	@FXML
	SokobanDisplayer sokobanDisplayer;

	@FXML
	Text stepsTakenText;

	@FXML
	Text elapsedTimeText;

	@FXML
	MenuItem openLevelScoreBtn;

	@FXML
	Text levelNameText;

	@FXML
	MenuItem showSolutionBtn;
	
	@FXML
	MenuItem showHintBtn;
	Stage stage;

	private ScoresWindowController scoreWindow;

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent WindowEv) {
				close();

			}

		});
	}

	// @FXML
	/** Initilazing the keys and checking which key was entered */
	public void initialize(URL location, ResourceBundle resources) {

		sokobanDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> sokobanDisplayer.requestFocus());
		sokobanDisplayer.addEventFilter(KeyEvent.KEY_PRESSED, (e) -> sokobanDisplayer.requestFocus());
		sokobanDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				LinkedList<String> command = new LinkedList<String>();
				if (event.getCode() == KeyCode.UP) {
					command.add("Move");
					command.add("up");
				}

				else if (event.getCode() == KeyCode.DOWN) {
					command.add("Move");
					command.add("down");
				}

				else if (event.getCode() == KeyCode.RIGHT) {
					command.add("Move");
					command.add("right");
				} else if (event.getCode() == KeyCode.LEFT) {
					command.add("Move");
					command.add("left");
				} else {
					return;
				}
				setChanged();
				notifyObservers(command);
			}
		});
	}

	/** Starting the game. */
	public void start() {
		stage.setTitle("SoKoban");
		try {
			Image ico = new Image(new FileInputStream("./Resources/Player.png"));
			stage.getIcons().add(ico);
		} catch (FileNotFoundException e) {
			System.out.println("Setting the file title has failed.");
		}
	}

	/** Opening a file from the desired path. */
	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Sokoban level");
		fc.setInitialDirectory(new File("./levels"));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("XML", "*.xml"), new FileChooser.ExtensionFilter("OBJ", "*.obj"));
		File chosen = fc.showOpenDialog(new Stage());
		if (chosen != null) {
			LinkedList<String> command = new LinkedList<String>();
			command.add("Load");
			command.add(chosen.getPath());
			setChanged();
			notifyObservers(command);
		}
	}

	/** Saving a file to the desired path */
	public void saveFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save Sokoban level");
		fc.setInitialDirectory(new File("./levels"));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("XML", "*.xml"), new FileChooser.ExtensionFilter("OBJ", "*.obj"));
		File chosen = fc.showSaveDialog(new Stage());
		if (chosen != null) {
			LinkedList<String> command = new LinkedList<String>();
			command.add("Save");
			command.add(chosen.getPath());
			setChanged();
			notifyObservers(command);
		}
	}

	/** Closing the window */
	public void close() {

		LinkedList<String> command = new LinkedList<String>();
		command.add("Exit");
		setChanged();
		notifyObservers(command);
		stage.close();

	}

	/** Displaying a level */
	@Override
	public void displayLevel(LevelData levelData, Integer steps) {
		sokobanDisplayer.setLevelData(levelData);
		levelNameText.setText(levelData.getName());
		openLevelScoreBtn.setDisable(false);
		showSolutionBtn.setDisable(false);
		showHintBtn.setDisable(false);
		sokobanDisplayer.redraw();
		if (steps != null)
			stepsTakenText.setText(steps.toString());
	}

	/** Displaying a message to the screen */
	@Override
	public void displayMessage(LinkedList<String> params) {

		String msg = String.join(" ", params);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText(msg);
				alert.showAndWait();
			}
		});
	}


	@Override
	public void updateTime(int seconds) {
		this.elapsedTimeText.setText(seconds / 60 + ":" + seconds % 60);
	}

	@Override
	public void userWin() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				AnchorPane root = null;
				try {
					root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/WinWindow.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				TextField userNameTxtBox = ((TextField) root.lookup("#userNameText"));
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.setTitle("Level finished");
				dialog.getDialogPane().setContent(root);
				dialog.setWidth(312);
				dialog.setHeight(450);
				dialog.setResizable(false);
				((Button) root.lookup("#submitRecord")).addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								String userName = userNameTxtBox.getText();
								if (userName != null && !userName.equals("")) {
									setChanged();
									LinkedList<String> params = new LinkedList<String>();
									params.add("SaveScore");
									params.add(userName);
									notifyObservers(params);
								}
								dialog.close();
								ScoresQuery query = new ScoresQuery();
								query.setLevelName(levelNameText.getText());
								openScoresTable(query);
							}
						});
				dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				dialog.showAndWait();
			}
		});
	}

	@Override
	public void setScores(Score[] scores) {
		scoreWindow.setScores(scores);
	}

	public void openScoresTableClick() {
		openScoresTable(null);
	}

	private void openScoresTable(ScoresQuery query) {
		MainWindowController parent = this;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				AnchorPane root = null;
				ScoresQuery localQuery = query;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ScoresTableWindow.fxml"));

				try {
					root = (AnchorPane) fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				scoreWindow = (ScoresWindowController) fxmlLoader.getController();
				scoreWindow.addObserver(parent);
				if (localQuery == null) {
					localQuery = new ScoresQuery();
				}
				scoreWindow.setQuery(localQuery);
				scoreWindow.search();
				Dialog<ButtonType> dialog = new Dialog<ButtonType>();
				dialog.setTitle("Score table");
				dialog.getDialogPane().setContent(root);
				dialog.setWidth(500);
				dialog.setHeight(400);
				dialog.setResizable(false);
				dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
				closeButton.managedProperty().bind(closeButton.visibleProperty());
				closeButton.setVisible(false);
				dialog.showAndWait();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		ArrayList<Object> list = new ArrayList<>();
		list.add("RequestScores");
		list.add(arg);
		notifyObservers(list);
	}

	public void openLevelScoresTableClick() {
		ScoresQuery query = new ScoresQuery();
		query.setLevelName(levelNameText.getText());
		openScoresTable(query);
	}
	
	public void showSolutionClick(){
		setChanged();
		List<String> params = new ArrayList<String>();
		params.add("Solution");
		notifyObservers(params);
	}
	
	public void showHintClick(){
		setChanged();
		List<String> params = new ArrayList<String>();
		params.add("Hint");
		notifyObservers(params);
	}
	
	@Override
	public void setSolution(String[] actions) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (String a : actions) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setChanged();
					ArrayList<String> list = new ArrayList<>();
					list.add("Move");
					list.add(a.split(" ")[1]);
					notifyObservers(list);
				}
			}
		}).start();
	}
}
