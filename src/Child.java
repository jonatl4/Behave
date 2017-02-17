import java.util.*;

public class Child extends User {
	private List<Integer> managerIds;
	private Mode mode;
	private List<Token> tokens;
	
	private Map<Integer, String> rewards;
	private boolean reset = false;
	private boolean lock = false;
	
	Scanner s = new Scanner(System.in);
	
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
	
	// Returns number of tokens that have been redeemed
	public int redeemTokens(int numTokens, Mode mode) {
		Iterator<Token> iter = tokens.iterator();
		int tokenCount = 0; 
		
		// Removes the tokens redeemed from List<Token>
		while(iter.hasNext()) {
			if (iter.next().getType().equals(mode) && numTokens > 0) {
				iter.remove();
				numTokens--;
				tokenCount++;
			}
		}
		
		return tokenCount;
	}
	
	public void redeemTokensByParent(Child child) {
		
		if (child.getMode().equals(Mode.POSITIVE)) {
			while (true) {
				System.out.println("This child's mode is currently POSITIVE. Redeem a reward?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				String redeem = s.nextLine();
				
				if (redeem.equals("1")) {
					int tokenAmount = 0;
					while (tokenAmount <= 0) {
						System.out.println("For how many tokens? (Must be greater than 0)");
						tokenAmount = s.nextInt();
						s.nextLine();
					}
					
					System.out.println("For what reward?");
					String reward = s.nextLine();
					
					int tokenCount = redeemTokens(tokenAmount, Mode.POSITIVE);
					
					System.out.println(reward.toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
					break;
					
				} else if (redeem.equals("2")) {
					break;
				} else {
					System.out.println("Invalid command.");
				}
			}
			
		} else if (child.getMode().equals(Mode.NEGATIVE)) {
			while (true) {
				System.out.println("This child's mode is currently NEGATIVE. Redeem a reward?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				String redeem = s.nextLine();
				
				if (redeem.equals("1")) {
					int tokenAmount = 0;
					while (tokenAmount <= 0) {
						System.out.println("For how many tokens? (Must be greater than 0)");
						tokenAmount = s.nextInt();
						s.nextLine();
					}
					
					System.out.println("For what reward (any)?");
					String reward = s.nextLine();
					
					int tokenCount = redeemTokens(tokenAmount, Mode.NEGATIVE);

					System.out.println(reward.toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
					break;
					
				} else if (redeem.equals("2")) {
					break;
				} else {
					System.out.println("Invalid command.");
				}
			}
		}
	}
	
	public void redeemTokensByChild(Child child) {
		
		if(child.getMode().equals(Mode.NEGATIVE)) {
			System.out.println("Uh oh, looks like you are in negative mode!");
			return;
		} else if (rewards.isEmpty()) {
			System.out.println("Uh oh, looks like your parent set no rewards!");
			return;
		}
		
		System.out.println("Here are some rewards you can use your tokens to redeem!\n");

		for(Map.Entry<Integer, String> entry : rewards.entrySet()) {
			System.out.println("Tokens Needed: " + entry.getKey() + "\tReward: " + entry.getValue() + "\n");
		}
		
		while (true) {
			System.out.println("Want to redeem one of the rewards above?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			String redeem = s.nextLine();
			
			if (redeem.equals("1")) {
				int tokenAmount = 0;
				int tokensOwned = tokens.size();
				
				boolean howManyTokens = true;
				while (howManyTokens) {
					System.out.println("For how many tokens? (You currently have " + tokensOwned + " tokens.)");
					tokenAmount = s.nextInt();
					s.nextLine();
					
					if (tokenAmount > tokensOwned) {
						System.out.println("You don't have enough tokens!");
						tokenAmount = 0;
					}
				
					for (Map.Entry<Integer, String> entry : rewards.entrySet()) {
						if(entry.getKey() == tokenAmount) {
							boolean confirmRedeem = true;
							while(confirmRedeem) {
								System.out.println("Are you sure you want to redeem " + entry.getValue() + " for " +
										entry.getKey() + " tokens?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								
								String confirm = s.nextLine();
								
								if (confirm.equals("1")) {
									int tokenCount = child.redeemTokens(tokenAmount, Mode.POSITIVE);
									System.out.println(entry.getValue().toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
									confirmRedeem = false;
								} else if (confirm.equals("2")) {
									confirmRedeem = false;
								} else {
									System.out.println("Invalid command.");
								}
							}
						}
					}
					howManyTokens = false;
				} 
			} else if (redeem.equals("2")) {
				return;
			} else {
				System.out.println("Invalid command.");
			}
			break;
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
	
	public void printChildInfo() {
		System.out.println("User ID: " + Integer.toString(id) + 
				"\tChild's Name: " + name + 
				"\tChild's Mode: " + mode + "\n");
	}
}
