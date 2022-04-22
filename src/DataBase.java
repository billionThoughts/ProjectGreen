import java.io.*;
import java.util.ArrayList;

public class DataBase {
	private ArrayList<UserAccount> userAccounts;
	private ArrayList<Material> materials;
	private ArrayList<Ticket> tickets;
	
	public DataBase() {
		if(!userAccountsDeserialization()) {
			userAccounts = new ArrayList<UserAccount>();
		}
		if(!materialsDeserialization()) {
			materials = new ArrayList<Material>();
		}
		if(!ticketsDeserialization()) {
			tickets = new ArrayList<Ticket>();
		}
		
	}
	
	public ArrayList<Material> getMaterials() {
		return materials;
	}
	
	public Material getSpecificMaterial(String name) {
		Material m = null;
		for(Material material : materials) {
			if(name.equals(material.getName()))
				m=material;
		}
		return m;
	}
	
	public Ticket getSpecificTicket(String name) {
		Ticket t = null;
		for(Ticket ticket : tickets) {
			if(name.equals(ticket.getName()))
				t=ticket;
		}
		return t;
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
	
	public UserAccount signedInAccountDeserialization() {
		UserAccount signedInAccount = null;
		try {
			FileInputStream fileIn = new FileInputStream("signedInAccount.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			signedInAccount = (UserAccount) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			System.out.println("signedInAccount.ser File Not Found HomeFrame signed in account deserialization");
		} catch (IOException e) {
			System.out.println("signedInAccount.ser IO Exception HomeFrame signed in account deserialization");
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found");
		}
		return signedInAccount;
	}
	
	public void saveSignedInAccount(UserAccount signedInAccount) {
		for(UserAccount a : userAccounts) {
			if(a.getUsername().equals(signedInAccount.getUsername()))
				userAccounts.set(userAccounts.indexOf(a), signedInAccount);
		}
		this.userAccountsSerialization();
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
	
	public boolean materialsDeserialization() {
		try {
			FileInputStream fileIn = new FileInputStream("materials.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			materials = (ArrayList<Material>) in.readObject();
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
	
	public void materialsSerialization() {
		try {
			FileOutputStream fileOut = new FileOutputStream("materials.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(materials);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("materials.ser File Not Found (DataBase materials serialization)");
		} catch (IOException e) {
			System.out.println("materials.ser IO Exception (DataBase materials serialization");
		}
	}
	
	public void addMaterial(Material m) {
		materials.add(m);
		materialsSerialization();
	}
	
	public boolean ticketsDeserialization() {
		try {
			FileInputStream fileIn = new FileInputStream("tickets.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			tickets = (ArrayList<Ticket>) in.readObject();
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
	
	public void ticketsSerialization() {
		try {
			FileOutputStream fileOut = new FileOutputStream("tickets.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tickets);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("tickets.ser File Not Found (DataBase tickets serialization)");
		} catch (IOException e) {
			System.out.println("tickets.ser IO Exception (DataBase tickets serialization)");
		}
	}
	
	public void addTicket(Ticket t) {
		tickets.add(t);
		ticketsSerialization();
	}
}
