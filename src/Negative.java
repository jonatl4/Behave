import java.io.Serializable;

public class Negative implements Mode, Serializable {
	
	@Override
	public void print() {
		System.out.println("Negative Mode");
	}
	
	@Override
	public String toString() {
		return "Negative";
	}
}
