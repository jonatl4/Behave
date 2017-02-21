import java.io.Serializable;

public class Positive implements Mode, Serializable {
	
	@Override
	public void print() {
		System.out.println("Positive Mode");
	}
	
	@Override
	public String toString() {
		return "Positive";
	}
}
