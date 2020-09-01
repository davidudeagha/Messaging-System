
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;

// Continuously reads from action queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread {
	private UserMessages clientMessageList;
	private PrintStream client;
	private BlockingQueue<ServerSenderAction> actions;

	public ServerSender(UserMessages u, PrintStream c, BlockingQueue<ServerSenderAction> a) {
		clientMessageList = u;
		client = c;
		actions = a;
	}

	/**
	 * Starts this server sender.
	 */
	public void run() {
		
		try {
			while (true) {
				ServerSenderAction action = actions.take();

				if (action == ServerSenderAction.SEND_MESSAGE) {
					if (clientMessageList.getCurrentMessage() != null) {
						client.println(clientMessageList.getCurrentMessage());
					}

					else {
						client.println("No Current Message");
					}

				}

				else if (action == ServerSenderAction.LOGOUT) {
					break;
				}

			}
		} catch (InterruptedException e) {
			Report.behaviour("A Server sender is ending");
		}
	}
}

/*
 * 
 * Throws InterruptedException if interrupted while waiting
 * 
 * See
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.
 * html#take--
 * 
 */
