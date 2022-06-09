package main;
import java.io.Serializable;

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
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