======================
# What is this?
======================
This is a small program, that works as a IRC bridge between IRC clients and TwitchIRC. The reason, why this program exists is because Twitch does not support the IRC command "WHO" and my IRC Client kept to send a WHO message, that got answered by the server with "Unknown Command" reply. This Program basically makes the WHO command not get sent to the server. The rest of the messages get relayed to twitch and back to the user.

======================
# How to build
======================
To build the program, you just need to run ``` ./gradlew[.bat] clean build ```
The built program will be in the ./build/libs/ directory.

======================
# How to use
======================
  - Start the program by using  ``` java -jar TIRC-XYZ.jar ```
  - Optionally, you can change the generated config file to change the Twitch and local address, and port and restart the program.
  - Connect to the program by connecting with your irc client using the host "localhost" and the port selected in the config file (by default 6667) using your normal twitch username and oauth token.
