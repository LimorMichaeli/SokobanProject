package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

	private int port;
	private ClientHandler ch;
	private volatile boolean stop;

	public MyServer(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		stop = false;
	}

	private void runServer() throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);
		while (!stop) {
			try {
				Socket aClient = server.accept(); // blocking call
				new Thread(new Runnable() { // WOW! we should control the number
											// of threads!
					public void run() {
						try {
							ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
							aClient.getInputStream().close();
							aClient.getOutputStream().close();
							aClient.close();
						} catch (IOException e) {
							/* ... */}
					}
				}).start();
			} catch (SocketTimeoutException e) {
				/* ... */}
		}
		server.close(); // WOW! we should wait for all threads before closing! }
	}

	public void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
					runServer();
				} catch (Exception e) {
				}
			}
		}).start();
	}

	public void stop() {
		stop = true;
	}
}
