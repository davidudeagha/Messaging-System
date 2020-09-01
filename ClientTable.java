//This class stores the map that store user-to-message list
//and user-to-actionqueue

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTable {

	private ConcurrentMap<String, UserMessages> messageTable = new ConcurrentHashMap<>();

	private ConcurrentMap<String, BlockingQueue<ServerSenderAction>> actionQueues = new ConcurrentHashMap<>();

	public void add(String userName) {
		messageTable.put(userName, new UserMessages());
		actionQueues.put(userName, new LinkedBlockingQueue<ServerSenderAction>());
	}

	public UserMessages getUserMessages(String userName) {
		return messageTable.get(userName);
	}

	public BlockingQueue<ServerSenderAction> getQueue(String userName) {
		return actionQueues.get(userName);
	}

	// Removes from table:
	public void remove(String userName) {
		messageTable.remove(userName);
		actionQueues.remove(userName);
	}

}
