import java.io.Serializable;

public class UserAccount implements Serializable {
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	
	public UserAccount (String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
}
