package server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Peer {
	private int port;
	private String ip;
	private StringProperty status;

	public Peer(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.status = new SimpleStringProperty();
		setStatusProperty("Wait for thread");
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public StringProperty statusProperty() {
		return this.status;
	}

	public void setStatusProperty(String status) {
		this.status.setValue(status);
	}

	@Override
	public String toString() {
		return this.ip + ":" + this.port;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Peer == false)
			return false;

		Peer param = (Peer) obj;
		return this.port == param.getPort() && this.ip.equals(param.ip);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
