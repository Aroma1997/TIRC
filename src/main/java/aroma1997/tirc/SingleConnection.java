package aroma1997.tirc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class SingleConnection implements Runnable {
	
	protected BufferedReader reader;
	protected BufferedWriter writer;
	private Side side;
	protected final ClientConnection conn;
	Thread thread;
	
	SingleConnection(ClientConnection conn) throws IOException {
		this(conn, Side.TWITCH);
	}

	SingleConnection(ClientConnection conn, Side side) throws IOException {
		this.side = side;
		this.conn = conn;
		this.reader = new BufferedReader(new InputStreamReader(conn.getSocketFor(getSide()).getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(conn.getSocketFor(getSide().getOpposite()).getOutputStream()));
		thread = new Thread(this);
		thread.start();
	}
	
	public Side getSide() {
		return side;
	}
	
	private void log(String message) {
		Main.debugLog("[" + side + "->" + side.getOpposite() + "]" + conn.user + ": " + message);
	}

	@Override
	public final void run() {
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				log("<" + line);
				processLine(line);
				writer.flush();
			}
			stopConnection();
		} catch (IOException e) {
			if (!(e instanceof SocketException) || !e.getMessage().equals("Socket closed")) {
				e.printStackTrace();
				//TODO error handling
			} else {
				onStopConnection();
			}
		}
		
	}
	
	protected void processLine(String line) throws IOException {
		send(line);
	}
	
	protected void send(String line) throws IOException {
		log(">" + line);
		writer.write(line);
		writer.newLine();
	}
	
	private void stopConnection() throws IOException {
		onStopConnection();
		conn.getSocketFor(side.getOpposite()).close();
	}
	
	protected void onStopConnection() {
		if (getSide() == Side.USER) {
			Socket socket = conn.getSocketFor(Side.USER);
			Main.log("Connection from " + socket.getInetAddress() + ":" + socket.getPort() + " terminated.");
		}
	}

}
