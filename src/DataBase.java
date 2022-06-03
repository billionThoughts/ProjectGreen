import java.io.*;
import java.util.ArrayList;

public class DataBase {
	private static DataBase instance = new DataBase();
	private ArrayList<UserAccount> userAccounts;
	private ArrayList<Material> materials;
	private ArrayList<Ticket> tickets;
	private int donations;
	
	private DataBase() {
		if(!userAccountsDeserialization()) {
			userAccounts = new ArrayList<UserAccount>();
		}
		if(!materialsDeserialization()) {
			materials = new ArrayList<Material>();
		}
		if(!ticketsDeserialization()) {
			tickets = new ArrayList<Ticket>();
		}
		if(!donationsDeserialization()) {
			donations = 0;
		}
	}
	
	public static DataBase getInstance() {
		return instance;
	}
	
	public int getDonations() {
		return donations;
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
		try (FileInputStream fileIn = new FileInputStream("userAccounts.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			userAccounts = (ArrayList<UserAccount>) in.readObject();
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
		try (FileOutputStream fileOut = new FileOutputStream("userAccounts.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(userAccounts);
		} catch (FileNotFoundException e) {
			System.out.println("userAccounts.ser File Not Found (DataBase userAccounts serialization)");
		} catch (IOException e) {
			System.out.println("userAccounts.ser IO Exception (DataBase userAccounts serialization)");
		}
	}
	
	public boolean checkParticularsAvailability(String username, String email) {
		for(UserAccount acc : userAccounts) {
			if(username.equals(acc.getUsername()) || email.equals(acc.getEmail())) {
				return false;
			}
		}
		return true;
	}
	
	public void addUserAccount(UserAccount acc) {
		userAccounts.add(acc);
		this.userAccountsSerialization();
	}
	
	public void saveSignedInAccount(UserAccount signedInAccount) {
		for(UserAccount a : userAccounts) {
			if(a.getUsername().equals(signedInAccount.getUsername()))
				userAccounts.set(userAccounts.indexOf(a), signedInAccount);
		}
		this.userAccountsSerialization();
	}
	
	public UserAccount authentication(String username, String password) {
		for(UserAccount acc : userAccounts) {
			if(username.equals(acc.getUsername()) && password.equals(acc.getPassword())) {
				return acc;
			}
		}
		return null;
	}
	
	public boolean materialsDeserialization() {
		try (FileInputStream fileIn = new FileInputStream("materials.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			materials = (ArrayList<Material>) in.readObject();
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
		try (FileOutputStream fileOut = new FileOutputStream("materials.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(materials);
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
		try (FileInputStream fileIn = new FileInputStream("tickets.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			tickets = (ArrayList<Ticket>) in.readObject();
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
		try (FileOutputStream fileOut = new FileOutputStream("tickets.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(tickets);
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
	
	public boolean donationsDeserialization() {
		try (FileInputStream fileIn = new FileInputStream("donations.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			donations = (int) in.readObject();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
	public void donationsSerialization() {
		try (FileOutputStream fileOut = new FileOutputStream("donations.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(donations);
		} catch (FileNotFoundException e) {
			System.out.println("donations.ser File Not Found (DataBase donations serialization)");
		} catch (IOException e) {
			System.out.println("donations.ser IO Exception (DataBase donations serialization)");
		}
	}
	
	public boolean tokenDonation(int amount, UserAccount signedInAccount) {
		if(signedInAccount.donateTokens(amount)) {
			this.donations += amount;
			this.donationsSerialization();
			return true;
		}
		return false;
	}
}
