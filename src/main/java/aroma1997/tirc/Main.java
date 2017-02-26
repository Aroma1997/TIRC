package aroma1997.tirc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {
		Config config = Config.loadConfigFile("config");
		ServerSocket socket = new ServerSocket(config.getLocalPort());
		while (true) {
			Socket clientSocket = socket.accept();
			ClientConnection connection = new ClientConnection(clientSocket, config);
			System.out.println("Received connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
		}
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static void debugLog(String message) {
		if (debuggingEnabled()) {
			System.err.println(message);
		}
	}
	
	public static boolean debuggingEnabled() {
		boolean debug = false;
		assert debug = true;
		return debug;
	}

}
