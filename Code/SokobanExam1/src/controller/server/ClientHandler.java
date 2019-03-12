package controller.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

	public void handleClient(InputStream is, OutputStream os);
}
