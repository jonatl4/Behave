import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;

public class Token implements Serializable{
	private int tokenId;
	private DateTime timestamp;
	private Mode type;
	private String note;
	
	private static int counter = 0;

	public Token(Mode type) {
		tokenId = ++counter;
		this.type = type;
		timestamp = new DateTime();
	}
	
	public Token(Mode type, String note) {
		tokenId = ++counter;
		this.type = type;
		this.note = note;
		timestamp = new DateTime();
	}
	
	public int getTokenId() {
		return tokenId;
	}
	
	public Mode getType() {
		return type;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setType(Mode type) {
		this.type = type;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public DateTime getTimestamp() {
		return timestamp;
	}
	
	public void printTokenInfo() {	
		System.out.println("Token ID: " + Integer.toString(tokenId) +
				"\tType: " + type + 
				"\tTimestamp: " + timestamp +
				"\tNote: " + note);
	}
}
