
public class Main {

	public static void main(String[] args) {
		
		DataBase db = new DataBase();
		
		//Initialization of materials.ser
		Material plastic = new Material("Plastic", 15);
		Material paper = new Material("Paper", 50);
		Material glass = new Material("Glass", 20);
		Material metal = new Material("Metal", 25);
		Material organic = new Material("Organic", 40);
		
		db.addMaterial(plastic);
		db.addMaterial(paper);
		db.addMaterial(glass);
		db.addMaterial(metal);
		db.addMaterial(organic);

		new SignInFrame();
	}

}
