import java.util.*;

public class Child extends User {
	private Mode mode;
	private List<Token> tokens;
	
	private Map<Integer, String> rewards;
	private boolean reset = false;
	private boolean lock = false;
	
	ModeFactory modeFactory = new ModeFactory();
	
	public Child(String name) {
		super(name);
		tokens = new ArrayList<Token>();
		rewards = new HashMap<Integer, String>();
		mode = modeFactory.getMode("POSITIVE");
	}
	public void addToken(int numTokens) {
		int tokenAmount = numTokens;
		
		for (int i = 0; i < tokenAmount; i++) {
			Token token = new Token(mode);
			tokens.add(token);
		}
		System.out.println("Token(s) have been added successfully for " + name + ".");
	}

	public void addToken(int numTokens, String note) {
		int tokenAmount = numTokens;
		
		for (int i = 0; i < tokenAmount; i++) {
			Token token = new Token(mode, note);
			tokens.add(token);
		}
		System.out.println("Token(s) have been added successfully.");
	}
	
	public void editToken(Token token, Mode type, String note) {
		Mode newType = type;
		String newNote = note;
		
		token.setType(newType);
		token.setNote(newNote);
		System.out.println("Token has been edited successfully.");
	}
	
	public void removeToken(Token token) {
		Iterator<Token> iter = tokens.iterator();
		
		while(iter.hasNext()) {
			Token t = iter.next();
			if (t.getTokenId() == (token.getTokenId())) {
				iter.remove();
			}
		}
		System.out.println("Token has been deleted successfully.");
	}
	
	public List<Token> getTokens() {
		return tokens;
	}
	
	public Map<Integer, String> getRewards() {
		return rewards;
	}
	
	public void addRewards(int cost, String reward) {
		int costInTokens = cost;
		String rewardInfo = reward;
		
		rewards.put(costInTokens, rewardInfo);
		System.out.println(reward.toUpperCase() + " has been added with a cost of " + cost + " tokens.");
	}
	
	// Returns number of tokens that have been redeemed
	public int redeemTokens(int numTokens, Mode mode) {
		Iterator<Token> iter = tokens.iterator();
		int tokenCount = 0;
		
		int tokenAmount = numTokens;
		Mode currentMode = mode;
		
		// Removes the tokens redeemed from List<Token>
		while(iter.hasNext()) {
			if (iter.next().getType().getClass().equals(currentMode.getClass()) && tokenAmount > 0) {
				iter.remove();
				tokenAmount--;
				tokenCount++;
			}
		}
		
		return tokenCount;
	}
	
	public void scheduleToken(int time, int numTokens) {
		Timer t = new Timer( );
		int timeInSeconds = time;
		int tokenAmount = numTokens;
		
		t.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		    	if (lock) {
		    		t.cancel();
		    		t.purge();
		    		lock = false;
		    		return;
		    	} else {
		    		addToken(tokenAmount);
		    		reset = true;
		    	}
		    }
		}, 0, time * 1000);
		System.out.println(tokenAmount + " tokens will be added every " + timeInSeconds + " seconds.");
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public boolean getReset() {
		return reset;
	}
	
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
	public boolean getLock() {
		return lock;
	}
	
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	public void printChildInfo() {
		System.out.println("User ID: " + Integer.toString(id) + 
				"\tChild's Name: " + name + 
				"\tChild's Mode: " + mode.toString() + "\n");
	}
}
