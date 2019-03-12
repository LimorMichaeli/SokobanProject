package boot;

import java.io.IOException;

import db.SolutionsGateway;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.*;
import server.MyServer;
import view.ConnectionsManagerViewController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Run extends Application {

	private final static String ip = "localhost";
	private final static String solutionServerAddr = "localhost:8080";
	private final static int listenPort = 1993;
	private static MyServer server;

	@Override
	public void start(Stage primaryStage) {
		try {
			IModel model = new ViewModel(server);
			server.startServer();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ConnectionsManagerView.fxml"));
			AnchorPane root = (AnchorPane) fxmlLoader.load();

			ConnectionsManagerViewController view = (ConnectionsManagerViewController) fxmlLoader.getController();
			view.setModel(model);

			Scene scene = new Scene(root, 390, 450);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			server = new MyServer(listenPort, new SolutionsGateway(solutionServerAddr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
		server.stopServer();
	}
}
