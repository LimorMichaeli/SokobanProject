package model.serverProtocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import model.data.level.Level;

public class SolutionsGateway {
	private int serverPort;
	private String serverHost;

	public SolutionsGateway(String host, int port) {
		serverHost = host;
		serverPort = port;
	}

	public SolutionRespone GetSolution(Level level) {
		Command cmd = new Command();
		SolutionRespone resp = null;
		cmd.setLevel(level);
		cmd.setName("solution");
		try {
			Socket socket = new Socket(this.serverHost, this.serverPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(cmd);
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			resp = (SolutionRespone) in.readObject();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resp;
	}
}
