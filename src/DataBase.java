import java.io.*;
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
	
	public void signedInAccountSerialization(UserAccount signedInAccount) {
		try {
			FileOutputStream fileOut = new FileOutputStream("signedInAccount.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(signedInAccount);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("signedInAccount.ser File Not Found (signed in account serialization)");
		} catch (IOException e) {
			System.out.println("signedInAccount.ser IO Exception ? (signed in account serialization)");
		}
	}
	
	public boolean authentication(String username, String password) {
		for(UserAccount acc : userAccounts) {
			if(username.equals(acc.getUsername()) && password.equals(acc.getPassword())) {
				signedInAccountSerialization(acc);
				return true;
			}
		}
		return false;
	}
}
