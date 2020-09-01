//This class receives commands from the client and
//performs operations on the server accordingly


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ServerReceiver extends Thread {
	private String myClientsName;
	private BufferedReader fromClient;
	private ClientTable clientTable;
	private RegisteredUsers users;
	private PrintStream toClient;

	public ServerReceiver(BufferedReader c, ClientTable t, RegisteredUsers u, PrintStream p) {
		myClientsName = null;
		fromClient = c;
		clientTable = t;
		users = u;
		toClient = p;
	}

	/**
	 * Starts this server receiver.
	 */
	public void run() {
		try {
			while (true) {
				String command = fromClient.readLine();

				if (command.equals("register")) {
					String userName = fromClient.readLine();
					String password = fromClient.readLine();

					if (users.userExists(userName)) {
						toClient.println("This user already exists");
					} else {
						User user = new User(userName, password);
						users.addUser(user);
						clientTable.add(userName);
						toClient.println("You registered successfully!");
					}

				}

				if (command.equals("login")) {
					String userName = fromClient.readLine();
					String password = fromClient.readLine();

					if (!(users.userExists(userName))) {
						toClient.println("This user does not exist");
					} else if ((users.userIsLoggedIn(userName))) {
						toClient.println("This user is already logged in");
					} else if (!users.passwordChecker(userName, password)) {
						toClient.println("Incorrect Password!");
					} else {
						myClientsName = userName;
						users.logInUser(userName);
						(new ServerSender(clientTable.getUserMessages(userName), toClient,
								clientTable.getQueue(userName))).start();
						toClient.println("You logged in successfully");
						toClient.print("Current Message: ");
						clientTable.getQueue(userName).offer(ServerSenderAction.SEND_MESSAGE);
						//Each user has an action queue and this operation adds the 
						//"send message" action to his queue and it sends the current message
					}
				}

				if (command.equals("logout")) {
					if (myClientsName == null) {
						toClient.println("You have to log in first");
					} else {
						toClient.println("You logged out successfully");
						users.logOutUser(myClientsName);
						clientTable.getQueue(myClientsName).offer(ServerSenderAction.LOGOUT);
						//this adds the logout action to user's action queue
						break;
					}
				}

				if (command.equals("send")) {
					String userName = fromClient.readLine();
					String text = fromClient.readLine();

					if (text != null) {
						Message msg = new Message(myClientsName, text);
						UserMessages recipientsMessageList = clientTable.getUserMessages(userName);
						if (recipientsMessageList != null) {
							recipientsMessageList.addMessage(msg);
							clientTable.getQueue(userName).offer(ServerSenderAction.SEND_MESSAGE);
						} else {
							Report.error("Message for unexistent client " + userName + ": " + text);
						}
					}
					else {
						return;
					}
				}

				if (command.equals("previous")) {
					if (myClientsName == null) {
						toClient.println("You have to log in first");
					} else {
						if (clientTable.getUserMessages(myClientsName).moveToPrevious()) {
							clientTable.getQueue(myClientsName).offer(ServerSenderAction.SEND_MESSAGE);
						} else {
							toClient.println("There is no previous message");
						}
					}
				}

				if (command.equals("next")) {
					if (myClientsName == null) {
						toClient.println("You have to log in first");
					} else {
						if (clientTable.getUserMessages(myClientsName).moveToNext()) {
							clientTable.getQueue(myClientsName).offer(ServerSenderAction.SEND_MESSAGE);
						} else {
							toClient.println("This is the last message");
						}
					}
				}

				if (command.equals("delete")) {
					if (myClientsName == null) {
						toClient.println("You have to log in first");
					} else {
						if (clientTable.getUserMessages(myClientsName).deleteCurrentMessage()) {
							clientTable.getQueue(myClientsName).offer(ServerSenderAction.SEND_MESSAGE);
						} else {
							toClient.println("You have no messages to delete");
						}
					}
				}

			}
		} catch (IOException e) {
			Report.error("Something went wrong with the client " + myClientsName + " " + e.getMessage());
			// No point in trying to close sockets. Just give up.

		}

		Report.behaviour("Server receiver ending");
	}
}
