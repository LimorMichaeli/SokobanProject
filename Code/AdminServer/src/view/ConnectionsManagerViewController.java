package view;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.IModel;
import server.Peer;

public class ConnectionsManagerViewController extends Observable implements Initializable {

	private IModel model;

	@FXML
	TableView peersTableView;

	@FXML
	TextField maxConnectionsTxt;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (this.model != null){
			peersTableView.setItems(this.model.getConnectedPeerList());
			Bindings.bindBidirectional(maxConnectionsTxt.textProperty(), this.model.getMaxConnections());
		}
	}

	public void setModel(IModel model) {
		this.model = model;
		if(maxConnectionsTxt!=null)
			Bindings.bindBidirectional(maxConnectionsTxt.textProperty(), this.model.getMaxConnections());

		if (peersTableView != null)
			peersTableView.setItems(this.model.getConnectedPeerList());
	}
	
	public void onDisconnectedClick() {
		if (peersTableView.getSelectionModel().getSelectedItem() != null) {
			Peer peer = (Peer) peersTableView.getSelectionModel().getSelectedItem();
			this.model.disconnectClient(peer);
		}
	}
	
	public void onStartServerClick() {
		this.model.startServer();
	}

	public void onStopServerClick() {
		this.model.stopServer();
	}

	public void onUpdateMaxConnectionsClick() {
		this.model.SetMaxConnectionsInServer();
	}

}
