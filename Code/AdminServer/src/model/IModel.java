package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import server.Peer;

public interface IModel {
	ObservableList<Peer> getConnectedPeerList();
	
	public void disconnectClient(Peer peer);

	SimpleStringProperty getMaxConnections();

	void stopServer();

	void SetMaxConnectionsInServer();

	void startServer();
}
