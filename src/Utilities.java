import java.io.*;
import java.util.*;


public class Utilities implements Serializable {
	
	public ArrayList<Parent> loadFile(String input) {
		ArrayList<Parent> users = new ArrayList<Parent>();
        try
        {
            FileInputStream fis = new FileInputStream(input);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList) ois.readObject();
            System.out.println("Success! User data has been loaded.");
            ois.close();
            fis.close();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
			System.out.println("Class not found");
			e.printStackTrace();
        }
        
		return users;
	}
	
	public void saveFile(List<Parent> users) {
    	
    	try {
    		FileOutputStream fos = new FileOutputStream("myfile");
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(users);
	        System.out.println("Success! User data has been saved.");
	        oos.close();
	        fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
