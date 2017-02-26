package aroma1997.tirc;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.EnumMap;
import java.util.Map;

public class ClientConnection {
	
	private final Map<Side, Socket> sockets = new EnumMap<>(Side.class);
	private final Map<Side, SingleConnection> relays = new EnumMap<>(Side.class);
	String user = "UNKNOWN";
	
	public ClientConnection(Socket socket, Config config) throws UnknownHostException, IOException {
		sockets.put(Side.USER, socket);
		Socket twitchSocket = new Socket(config.getTwitchIRC(), config.getTwitchPort());
		sockets.put(Side.TWITCH, twitchSocket);
		for (Side side : Side.values()) {
			Socket currentSocket = sockets.get(side);
			System.out.println("Socket " + currentSocket.getInetAddress() + " on port " + currentSocket.getPort() + " for side " + side);
			relays.put(side, side.getConnection(this));
		}
	}
	
	public Socket getSocketFor(Side side) {
		return sockets.get(side);
	}
	
	public SingleConnection getRelayFor(Side side) {
		return relays.get(side);
	}

}
