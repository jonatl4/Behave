import java.util.*;

public class ChildManager extends User{
	private List<Child> children;
	
	public ChildManager(String name) {
		super(name);
		children = new ArrayList<Child>();
	}
	
	public void addChild(Child child) {
		children.add(child);
		System.out.println("Child was added.");
	}
	
	public void editChild(Child child, String newName) {
		child.setName(newName);
		System.out.println("Child was edited successfully!");
	}
	
	public void removeChild(Child child) {
		children.remove(child);
		System.out.println("Child was deleted successfully!");
	}
	
	public List<Child> getChildren() {
		return children;
	} 
}
