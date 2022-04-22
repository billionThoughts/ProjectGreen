import java.io.Serializable;

public class Ticket implements Serializable {
	private String name;
	private int cost;
	
	public Ticket(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public String getName() {
		return name;
	}
}