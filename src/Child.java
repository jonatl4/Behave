import java.util.*;

public class Child extends User {
	private List<Integer> managerIds;
	private Mode mode;
	private List<Token> tokens;
	
	private Map<Integer, String> rewards;
	private boolean reset = false;
	private boolean lock = false;
	
	public Child(String name) {
		super(name);
		managerIds = new ArrayList<Integer>();
		tokens = new ArrayList<Token>();
		rewards = new HashMap<Integer, String>();
		mode = Mode.POSITIVE;
	}
	public void addToken(int numTokens) {
		for (int i = 0; i < numTokens; i++) {
			Token token = new Token(mode);
			tokens.add(token);
		}
		System.out.println("Token(s) have been added successfully for " + name + ".");
	}

	public void addToken(int numTokens, String note) {
		for (int i = 0; i < numTokens; i++) {
			Token token = new Token(mode, note);
			tokens.add(token);
		}
		System.out.println("Token(s) have been added successfully.");
	}
	
	public void editToken(Token token, Mode newType, String newNote) {
		token.setType(newType);
		token.setNote(newNote);
		System.out.println("Token has been edited successfully.");
	}
	
	public void removeToken(Token token) {
		tokens.remove(token);
		System.out.println("Token has been deleted successfully.");
	}
	
	public List<Token> getTokens() {
		return tokens;
	}
	
	public Map<Integer, String> getRewards() {
		return rewards;
	}
	
	public void addRewards(int cost, String reward) {
		rewards.put(cost, reward);
		System.out.println(reward.toUpperCase() + " has been added with a cost of " + cost + " tokens.");
	}
	
	public void redeemTokens(int numTokens) {
		Iterator<Token> iter = tokens.iterator();
		while(iter.hasNext()) {
			if (iter.next().getType().equals(Mode.POSITIVE) && numTokens > 0) {
				iter.remove();
				numTokens--;
			}
		}
	}
	
	public void scheduleToken(int time, int tokenAmount) {
		Timer t = new Timer( );
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
		System.out.println(tokenAmount + " tokens will be added every " + time + " seconds.");
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
}
