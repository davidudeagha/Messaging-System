import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

	private String userName;
	private byte[] password;

	public static byte[] hashPassword(String password) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			return hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return password.getBytes(StandardCharsets.UTF_8);
		}

	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = hashPassword(password);
	}

	public String getUserName() {
		return userName;
	}

	public byte[] getPasswordHash() {
		return password;
	}
}
