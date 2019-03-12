package boot;

import java.io.File;

import controller.MySokobanController;
import controller.server.ClientHandler;
import controller.server.MyServer;
import db.ScoresDBDataMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import model.MyModel;
import model.serverProtocol.SolutionsGateway;
import view.CLI;
import view.MainWindowController;

/** The graphic user interface settings */
public class Main extends Application {

	private final static String ip = "localhost";
	private final static int port = 1993;

	@Override
	public void start(Stage primaryStage) {

		try {

			FXMLLoader fxl = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
			BorderPane root = (BorderPane) fxl.load();
			MainWindowController view = fxl.getController();

			MyModel model = new MyModel(new ScoresDBDataMapper(ip, port), new SolutionsGateway(ip,port));
			MySokobanController controller = new MySokobanController(model, view);
			model.addObserver(controller);
			view.addObserver(controller);
			view.setStage(primaryStage);
			view.start();
			Scene scene = new Scene(root, 800, 600);
			File soundtrack = new File("./Resources/SokobanSong.mp3");
			if (soundtrack.exists()) {
				Media media = new Media(soundtrack.toURI().toString());
				AudioClip player = new AudioClip(media.getSource());

				player.play();

			}

			primaryStage.setScene(scene);
			primaryStage.show();

			if (!getParameters().getRaw().isEmpty()) {
				if (getParameters().getRaw().get(0).equals("-server")) {

					CLI ch = new CLI(System.out, System.in);
					ch.addObserver(controller);
					MyServer server;
					server = new MyServer(Integer.parseInt(getParameters().getRaw().get(1)), (ClientHandler) ch);

					controller.setMyServer(server);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading the game, exitting now.");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
