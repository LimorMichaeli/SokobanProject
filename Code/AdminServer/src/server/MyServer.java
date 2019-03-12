package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import db.SolutionsGateway;

public class MyServer extends Observable implements Observer {
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
	private ConcurrentHashMap<Peer, Future<Boolean>> connectionsManager;
	private Thread listenThread;
	private SolutionsGateway solutionGateway;
	private boolean isEnd = false;
	private int maxConnections;
	private int serverPort;

	public MyServer(int port, SolutionsGateway solutionGateway) throws IOException {
		this.solutionGateway = solutionGateway;
		this.threadPool = Executors.newFixedThreadPool(10);
		this.maxConnections = 10;
		this.connectionsManager = new ConcurrentHashMap<>();
		this.serverPort = port;
	}

	public void forceDisconnectClient(Peer peer) {
		Future<Boolean> future = this.connectionsManager.get(peer);
		if (future != null) {
			future.cancel(true);
			this.connectionsManager.remove(peer);
			setChanged();
			List<Object> params = new ArrayList<Object>();
			params.add("disconnected");
			params.add(peer);
			notifyObservers(params);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (this.connectionsManager.containsKey(arg1)) {
			this.connectionsManager.remove(arg1);
			setChanged();
			List<Object> params = new ArrayList<Object>();
			params.add("disconnected");
			params.add(arg1);
			notifyObservers(params);
		}
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public void stopServer() {
		if (this.listenThread == null)
			return;
		this.isEnd = true;
		this.listenThread.interrupt();
		this.listenThread = null;
		for (Future<?> f : this.connectionsManager.values())
			f.cancel(true);
		this.connectionsManager.clear();
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		if (this.listenThread != null)
			return;
		this.isEnd = false;
		MyServer server = this;
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.listenThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isEnd == false) {
					try {
						Socket clientSocket;
						try {
							clientSocket = serverSocket.accept();
						} catch (SocketException e) {
							continue;
						}
						if (maxConnections <= connectionsManager.size()) {
							clientSocket.close();
							continue;
						}
						Peer peer = new Peer(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
						PeerCallable callable = new PeerCallable(clientSocket, peer, solutionGateway);
						callable.addObserver(server);
						Future<Boolean> future = threadPool.submit(callable);
						connectionsManager.put(peer, future);
						server.setChanged();
						List<Object> params = new ArrayList<Object>();
						params.add("connected");
						params.add(peer);
						server.notifyObservers(params);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		this.listenThread.start();
	}
}
