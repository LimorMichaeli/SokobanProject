package model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.MyServer;
import server.Peer;

public class ViewModel implements IModel, Observer {

	private final ObservableList<Peer> connectedPeers = FXCollections.observableArrayList();
	private MyServer server;
	private SimpleStringProperty maxConnections;

	public ViewModel(MyServer server) {
		this.server = server;
		this.server.addObserver(this);
        this.maxConnections=new SimpleStringProperty(String.valueOf(server.getMaxConnections()));
	}

	@Override
	public void disconnectClient(Peer peer) {
		this.server.forceDisconnectClient(peer);
	}

	@Override
	public ObservableList<Peer> getConnectedPeerList() {
		return this.connectedPeers;
	}

	@Override
	public void update(Observable o, Object arg) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				List<Object> params = (List<Object>) arg;
				if (((String) params.get(0)).equals("disconnected"))
					connectedPeers.remove(params.get(1));
				else
					connectedPeers.add((Peer)params.get(1));
			}
		});

	}
	
	@Override
	public SimpleStringProperty getMaxConnections() {
		return this.maxConnections;
	}
	
	@Override
	public void SetMaxConnectionsInServer() {
		try {
			int num = Integer.parseInt(this.maxConnections.getValue());
			if (num < 0)
				throw new NumberFormatException();
			this.server.setMaxConnections(num);
		} catch (NumberFormatException e) {
			this.maxConnections.setValue(String.valueOf(this.server.getMaxConnections()));
		}
	}

	@Override
	public void stopServer() {
		this.server.stopServer();
	}

	@Override
	public void startServer() {
		this.server.startServer();
	}

}
