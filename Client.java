
// Usage:
//        java Client server-hostname
//
// After initializing and opening appropriate sockets, it
// reads from the command line and send input to the server.
// it then starts a client receiver thread to receive messages.
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {

	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 1) {
			Report.errorAndGiveUp("Usage: java Client server-hostname");
		}

		// Initialize information:
		String hostname = args[0];

		// Open sockets:
		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;

		String currentlyLoggedIn = null;

		try {
			server = new Socket(hostname, Port.number);
														
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			Report.errorAndGiveUp("Unknown host: " + hostname);
		} catch (IOException e) {
			Report.errorAndGiveUp("The server doesn't seem to be running " + e.getMessage());
		}

		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

		try {
			
			while (true) {
				String command = user.readLine();

				if (command.equals("register")) {
					String userName = user.readLine();
					String password = user.readLine();
					toServer.println(command);
					toServer.println(userName);
					toServer.println(password);
					String response = fromServer.readLine();
					System.out.println(response);
				}

				if (command.equals("login")) {
					if (currentlyLoggedIn != null) {
						System.out.println("You are already logged in");
						continue;
					}
					String userName = user.readLine();
					String password = user.readLine();
					toServer.println(command);
					toServer.println(userName);
					toServer.println(password);
					String response = fromServer.readLine();
					System.out.println(response);
					if (response.equals("You logged in successfully")) {
						currentlyLoggedIn = userName;
						new ClientReceiver(fromServer).start();
					}
				}

				if (command.equals("logout")) {
					if (currentlyLoggedIn == null) {
						System.out.println("You have to be logged in first");
						continue;
					}
					toServer.println(command);
					currentlyLoggedIn = null;
					break;
				}

				if (command.equals("send")) {
					if (currentlyLoggedIn == null) {
						System.out.println("You have to be logged in first");
						continue;
					}
					String userName = user.readLine();
					String text = user.readLine();
					toServer.println(command);
					toServer.println(userName);
					toServer.println(text);
				}
				
				if(command.equals("previous") ||command.equals("next")  ||command.equals("delete")) {
					if (currentlyLoggedIn == null) {
						System.out.println("You have to be logged in first");
						continue;
					}
					toServer.println(command);
				}

			}
		} catch (IOException e) {
			Report.errorAndGiveUp("Communication broke in Client" + e.getMessage());
		}

		// Wait for them to end and close sockets.
		try {
			Report.behaviour("Client ended");
			toServer.close();
			fromServer.close(); 
			server.close();

		} catch (IOException e) {
			Report.errorAndGiveUp("Something wrong " + e.getMessage());
		}
		Report.behaviour("User has logged out. Goodbye.");
	}
}
