import java.util.ArrayList;

public class UserMessages {

	private ArrayList<Message> messages = new ArrayList<>();
	private int currentMessage = 0;

	public Message getCurrentMessage() {
		if (messages.isEmpty()) {
			return null;
		} else {
			return messages.get(currentMessage);
		}
	}

	public int getCurrentMessageIndex() {
		return currentMessage;
	}

	public boolean moveToPrevious() {
		if (currentMessage - 1 < 0) {
			return false;
		} else {
			currentMessage--;
			return true;
		}
	}

	public boolean moveToNext() {
		if (currentMessage + 1 >= messages.size()) {
			return false;
		} else {
			currentMessage++;
			return true;
		}
	}

	public boolean deleteCurrentMessage() {
		if (messages.isEmpty()) {
			return false;
		} else {
			messages.remove(currentMessage);
			if (currentMessage == messages.size()) {
				currentMessage--;
			}
			return true;
		}
	}

	public void addMessage(Message msg) {
		messages.add(msg);
		currentMessage = messages.size() - 1;
	}
}