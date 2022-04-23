import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
		
		transactions = new ArrayList<Transaction>();
	}
	
	private void recycledInitialization() {
		DataBase db = new DataBase();
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
	
	public ArrayList<Transaction> getTransactions(){
		return transactions;
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
	
	public boolean isTicketAffordable(Ticket t) {
		boolean flag = false;
		if(tokens >= t.getCost())
			flag = true;
		return flag;
	}
	
	public void buyTicket(Ticket t) {
		this.tokens -= t.getCost();
	}
	
	public void donateTokens(int amount) {
		this.tokens -= amount;
	}
	
	public void addTransaction(Transaction t) {
		transactions.add(t);
	}
	
	public void removeTransaction(Transaction t) {
		Transaction trToRemove = null;
		for(Transaction transaction : transactions) {
			if((t.getAmount() == transaction.getAmount()) && (t.getStringPeriod().equals(transaction.getStringPeriod()))) {
				trToRemove = transaction;
			}
		}
		transactions.remove(trToRemove);
	}
	
	public void makeTransaction(Transaction t) {
		if(t instanceof Staking) {
			this.tokens -= t.getAmount();
		}
		/*
		else if(t instanceof Lending) {
			this.tokens -= t.getAmount();
		}
		else if(t instanceof Borrowing) {
			this.tokens += t.getAmount();
		}
		*/
		this.addTransaction(t);
	}
	
	public int undoTransaction(Transaction t) {
		if(t instanceof Staking) {
			this.tokens += t.payment();
		}
		removeTransaction(t);
		return t.payment();
		
		/*
		else if(type.equals("PAY BACK")) {
			if(this.tokens >= ((Borrowing) t).getRealTotalAmount()) {
				this.tokens -= ((Borrowing) t).getRealTotalAmount();
				amount = ((Borrowing) t).getRealTotalAmount();
			}
			else JOptionPane.showMessageDialog(null, "You don't have enough tokens");
		}
		else if(type.equals("WITHDRAW")) {
			this.tokens += t.getTotalAmount();
			amount = t.getTotalAmount();
		}
		*/
	}
}