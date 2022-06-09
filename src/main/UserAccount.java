package main;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private int tokens;
	private HashMap<Material, Integer> recycled; //Map of recyclable materials and their quantity
	private ArrayList<Transaction> transactions;
	
	public UserAccount (String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.tokens = 0;
		
		recycled = new HashMap<>();
		this.recycledInitialization();
		
		transactions = new ArrayList<>();
	}
	
	private void recycledInitialization() {
		DataBase db = DataBase.getInstance();
		for(Material material : db.getMaterials()) {
			recycled.put(material, 0);
		}
	}
	
	public HashMap<Material, Integer> getRecycled() {
		return recycled;
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
	
	public void setTokens(int amount) {
		tokens = amount;
	}
	
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	
	//Adds the reward of the material to users tokens and increases recyclable material quantity by 1 in map recycled
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
	
	//Checks if tokens are enough to buy ticket
	public boolean isTicketAffordable(Ticket t) {
		boolean flag = false;
		if(tokens >= t.getCost())
			flag = true;
		return flag;
	}
	
	public void buyTicket(Ticket t) {
		this.tokens -= t.getCost();
	}
	
	//Checks if tokens are enough and donates the amount, else returns false
	public boolean donateTokens(int amount) {
		if(tokens>=amount) {
			this.tokens -= amount;
			return true;
		}
		return false;
	}
	
	public void addTransaction(Transaction t) {
		transactions.add(t);
	}
	
	//Removes transaction t from transactions list
	public void removeTransaction(Transaction t) {
		Transaction trToRemove = null;
		for(Transaction transaction : transactions) {
			if((t.getAmount() == transaction.getAmount()) && (t.getStringPeriod().equals(transaction.getStringPeriod()))) {
				trToRemove = transaction;
			}
		}
		transactions.remove(trToRemove);
	}
	
	//Increases or decreases tokens by the transaction amount and adds transaction to the transactions list
	public void makeTransaction(Transaction t) {
		if(t instanceof Staking) {
			this.tokens -= t.getAmount();
		}
		else if(t instanceof Lending) {
			this.tokens -= t.getAmount();
		}
		else if(t instanceof Borrowing) {
			this.tokens += t.getAmount();
		}
		this.addTransaction(t);
	}
	
	/*
	 * Increases or decreases tokens by the transaction amount, 
	 * removes transaction from the transactions list, returns amount to pay 
	 */
	public int undoTransaction(Transaction t) {
		if(t instanceof Staking) {
			this.tokens += t.payment();
		}
		else if(t instanceof Borrowing) {
			this.tokens -= t.payment();
		}
		else if(t instanceof Lending) {
			this.tokens += t.payment();
		}
		
		removeTransaction(t);
		return t.payment();
	}
	
	//Looks for a transaction in transactions list and returns it
	public Transaction getSelectedTransaction(String amount, String period, String classType) {
		Transaction selectedTransaction = null;
		for(Transaction t : transactions) {
			String tAmount = Integer.toString(t.getAmount());	
			String tPeriod = t.getStringPeriod();
			if((tAmount.equals(amount)) 
					&& (tPeriod.equals(period)) && (t.getClass().toString().equals(classType))) {
				selectedTransaction = t;
				break;
			}
		}
		return selectedTransaction;
	}
	
	//Calculates the total amount of tokens user has borrowed
	public int calculateTotalBorrowings() {
		int sum = 0;
		for(Transaction t : transactions) {
			if(t instanceof Borrowing) {
				sum+=t.getAmount();
			}
		}
		return sum;
	}
	
	//Checks if tokens are enough to pay back a borrowing
	public boolean isPayBackAffordable(Transaction t) {
		if(tokens >= t.payment()) {
			return true;
		}
		return false;
	}
}