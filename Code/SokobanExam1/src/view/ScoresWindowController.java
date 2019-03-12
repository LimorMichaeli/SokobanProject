package view;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import db.Score;
import db.ScoresQuery;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ScoresWindowController extends Observable implements Initializable {

	private final ObservableList<Score> scoresList = FXCollections.observableArrayList();

	@FXML
	TableView scoresTableView;

	@FXML
	TextField pageNumberTxt;
	@FXML
	TextField userNameTxt;
	@FXML
	TextField levelNameTxt;

	@FXML
	ChoiceBox orderByChoiceBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scoresTableView.setItems(scoresList);
		orderByChoiceBox.setItems(FXCollections.observableArrayList("id", "levelName", "userName", "seconds", "steps"));
		orderByChoiceBox.setValue("seconds");
	}

	public void onSearchClick() {
		search();
	}

	public void search() {
		ScoresQuery query = new ScoresQuery();
		if (userNameTxt.getText() == null || userNameTxt.getText().equals(""))
			query.setUserName(null);
		else
			query.setUserName(userNameTxt.getText());

		if (levelNameTxt.getText() == null || levelNameTxt.getText().equals(""))
			query.setLevelName(null);
		else
			query.setLevelName(levelNameTxt.getText());

		query.setOrderBy((String) orderByChoiceBox.getValue());
		try {
			query.setPage(Integer.parseInt(pageNumberTxt.getText()) - 1);
			if (query.getPage() < 0)
				query.setPage(0);
		} catch (NumberFormatException ex) {
			query.setPage(0);
		}
		setChanged();
		notifyObservers(query);
	}

	public void setScores(Score[] scores) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				scoresList.clear();
				scoresList.addAll(scores);
			}
		});
	}

	@FXML
	public void onMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY && scoresTableView.getSelectionModel().getSelectedItem() != null) {
			Score score = (Score) scoresTableView.getSelectionModel().getSelectedItem();
			ScoresQuery query = new ScoresQuery();
			query.setUserName(score.getUserName());
			setQuery(query);
			search();
		}

	}

	public void setQuery(ScoresQuery query) {
		userNameTxt.setText(query.getUserName());
		levelNameTxt.setText(query.getLevelName());
		orderByChoiceBox.setValue(query.getOrderBy());
		pageNumberTxt.setText(((Integer) query.getPage()).toString());
	}

}
