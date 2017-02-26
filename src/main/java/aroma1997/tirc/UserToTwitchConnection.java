package aroma1997.tirc;

import java.io.IOException;

public class UserToTwitchConnection extends SingleConnection {

	UserToTwitchConnection(ClientConnection conn) throws IOException {
		super(conn, Side.USER);
	}
	
	protected void processLine(String line) throws IOException {
		int spaceIndex = line.indexOf(' ');
		String identifier = line.substring(0, spaceIndex);
		String message = line.substring(spaceIndex + 1);
		switch (identifier) {
		case "WHO": //Do not relay WHO command, because Twitch doesn't support that.
			break;
		case "NICK":
			conn.user = message;
			send("CAP REQ :twitch.tv/membership");
		default:
			send(line);
		}
	}
}
