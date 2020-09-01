import java.util.ArrayList;
import java.util.Arrays;

public class RegisteredUsers {

	private ArrayList<User> users;
	private ArrayList<String> loggedInUsers;

	public RegisteredUsers() {
		users = new ArrayList<User>();
		loggedInUsers = new ArrayList<String>();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public boolean userExists(String userName) {
		for (int i = 0; i < users.size(); i++) {
			if (((users.get(i)).getUserName()).equals(userName)) {
				return true;
			} 
		}
		return false;
	}
	
	public boolean passwordChecker(String userName, String password) {
		byte[] passwordHash = User.hashPassword(password);
		for (int i = 0; i < users.size(); i++) {
			if (((users.get(i)).getUserName()).equals(userName)) {
				if (Arrays.equals(users.get(i).getPasswordHash(), passwordHash)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void logInUser(String userName) {
		loggedInUsers.add(userName);
	}
	
	public boolean userIsLoggedIn(String userName) {
		return loggedInUsers.contains(userName);
	}
	
	public void logOutUser(String userName) {
		loggedInUsers.remove(userName);
	}
}
