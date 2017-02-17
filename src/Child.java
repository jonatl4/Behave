import java.util.*;

public class Child extends User {
	private List<Integer> managerIds;
	private Mode mode;
	private List<Token> tokens;
	
	private Map<Integer, String> rewards;
	private boolean reset = false;
	private boolean lock = false;
	
	Scanner s = new Scanner(System.in);
	ModeFactory modeFactory = new ModeFactory();
	
	public Child(String name) {
		super(name);
		managerIds = new ArrayList<Integer>();
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
	
	public void redeemTokensByParent(Child child) {
		
		if (mode instanceof Positive) {
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
					
					int tokenCount = redeemTokens(tokenAmount, modeFactory.getMode("POSITIVE"));
					
					System.out.println(reward.toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
					break;
					
				} else if (redeem.equals("2")) {
					break;
				} else {
					System.out.println("Invalid command.");
				}
			}
			
		} else if (mode instanceof Negative) {
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
					
					int tokenCount = redeemTokens(tokenAmount, modeFactory.getMode("NEGATIVE"));

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
		
		if(mode instanceof Negative) {
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
									int tokenCount = child.redeemTokens(tokenAmount, modeFactory.getMode("POSITIVE"));
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
