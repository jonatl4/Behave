import java.io.Serializable;

public class User implements Serializable {
	
	public int id;
	public String name;
	private static int counter = 0;
	
	public User() {
		id = 0;
		name = "John Doe";
	}
	
	public User(String name) {
		id = ++counter;
		this.name = name;
	}
	
	public int getUserId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setUserId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
