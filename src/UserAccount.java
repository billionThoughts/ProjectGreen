import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class UserAccount implements Serializable {
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private int tokens;
	private HashMap<Material, Integer> recycled;
	
	public UserAccount (String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.tokens = 0;
		
		recycled = new HashMap<>();
		this.recycledInitialization();
	}
	
	public void recycledInitialization() {
		DataBase db = new DataBase();
		for(Material material : db.getMaterials()) {
			recycled.put(material, 0);
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getTokens() {
		return tokens;
	}
	
	public void recycleMaterial(Material m) {
		this.tokens += m.getReward();
		
		Iterator<Material> i = recycled.keySet().iterator();
		while(i.hasNext()) {
			Material key = i.next();
			if(key.getName().equals(m.getName())) {
				recycled.put(key, recycled.get(key) + 1);
			}
		}
	}
}
