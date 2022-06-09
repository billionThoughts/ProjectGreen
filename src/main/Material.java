package main;
import java.io.Serializable;

public class Material implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int reward;
	
	public Material (String name, int reward) {
		this.name = name;
		this.reward = reward;
	}

	public String getName() {
		return name;
	}
	
	public int getReward() {
		return reward;
	}
}
