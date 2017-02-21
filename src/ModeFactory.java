import java.io.Serializable;

public class ModeFactory implements Serializable {
	
	public Mode getMode(String modeType) {
		if (modeType == null) {
			return null;
		}
		
		if (modeType.equalsIgnoreCase("POSITIVE")) {
			return new Positive();
		} else if (modeType.equalsIgnoreCase("NEGATIVE")) {
			return new Negative();
		}
		
		return null;
	}
}
