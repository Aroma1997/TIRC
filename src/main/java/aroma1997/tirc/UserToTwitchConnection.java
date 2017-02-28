package aroma1997.tirc;

import java.io.IOException;

public class UserToTwitchConnection extends SingleConnection {

	UserToTwitchConnection(ClientConnection conn) throws IOException {
		super(conn, Side.USER);
	}
	
	@Override
	protected void processLine(String line) throws IOException {
		int spaceIndex = line.indexOf(' ');
		String identifier = line.substring(0, spaceIndex);
		String message = line.substring(spaceIndex + 1);
		switch (identifier) {
		case "WHO": //Do not relay WHO command, because Twitch doesn't support that.
			break;
		case "WHOIS":
			SingleConnection other = conn.getRelayFor(getSide().getOpposite());
			other.send(":" + getUserDomain(conn.user, true) + " 319 " + conn.user + " " + message + " :Unknown. Functionality not supported."); // Channels that person is in.
			other.send(":" + getUserDomain(conn.user, true) + " 312 " + conn.user + " " + message + " " + conn.getSocketFor(Side.TWITCH).getInetAddress().getHostName()); //Connected server
			other.send(":" + getUserDomain(conn.user, true) + " 311 " + conn.user + " " + message + " ~" + message + " " + getUserDomain(message, false) + " * " + message); //Username and client name.
			other.send(":" + getUserDomain(conn.user, true) + " 318 " + conn.user + " " + message + " :End of /WHOIS list.");
			other.writer.flush();
			break;
		case "NICK":
			conn.user = message;
			//Request membership capability, so we get messaged, who is online and who isn't.
			send("CAP REQ :twitch.tv/membership");
		case "JOIN":
			//we need to lowercase nicknames and channel names
			line = identifier + " " + message.toLowerCase();
		default:
			send(line);
		}
	}
	
	private String getUserDomain(String user, boolean actual) {
		if (!actual && conn.user.equals(user)) {
			return conn.getSocketFor(Side.USER).getInetAddress().getHostName();
		} else {
			return user + ".tmi.twitch.tv";
		}
	}
}
