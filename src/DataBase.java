import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBase {
	private ArrayList<UserAccount> userAccounts;
	
	public DataBase() {
		if(!userAccountsDeserialization()) {
			userAccounts = new ArrayList<UserAccount>();
		}
	}
	
	public boolean userAccountsDeserialization() {
		try {
			FileInputStream fileIn = new FileInputStream("userAccounts.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			userAccounts = (ArrayList<UserAccount>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
	public void userAccountsSerialization() {
		try {
			FileOutputStream fileOut = new FileOutputStream("userAccounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(userAccounts);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("userAccounts.ser File Not Found (DataBase userAccounts serialization)");
		} catch (IOException e) {
			System.out.println("userAccounts.ser IO Exception (DataBase userAccounts serialization)");
		}
	}
	
	public boolean checkParticularsAvailability(String username, String email) {
		boolean flag = true;
		for(UserAccount acc : userAccounts) {
			if(username.equals(acc.getUsername()) || email.equals(acc.getEmail())) {
				flag = false;
			}
		}
		return flag;
	}
	
	public void addUserAccount(UserAccount acc) {
		userAccounts.add(acc);
		this.userAccountsSerialization();
	}
}
