import java.util.*;

public class CLIApplication implements IApplication {
	
	private List<Parent> parentUsers;
	private List<Child> childUsers;	
	
	
	public CLIApplication() {
		parentUsers = new ArrayList<Parent>();
		childUsers = new ArrayList<Child>();
	}
	
	public void start() {
		System.out.println("Welcome to Behave!");
		Scanner s = new Scanner(System.in);
		
		while(true) {

			System.out.println("Are you a (P)arent or a (C)hild?");
			
			String selection = s.nextLine();
			
			boolean parentMenu;
			boolean childMenu;
			Parent parent;
			
			// Selection menu for Parent or Child
			if (selection.equals("P")) {
				
				// Check if new parent
				if (parentUsers.isEmpty()) {
					System.out.println("Welcome parent! What is your name?");
					String parentName = s.nextLine();
					parent = new Parent(parentName);
					parentUsers.add(parent);
				} else {
					parent = parentUsers.get(0);
					System.out.println("Welcome back, " + parent.getName() + "\n");
				}
				
				parentMenu = true;
			
				while (parentMenu) {
					System.out.println("\n" + parent.getName() + ", select an action:\n");
					displayParentMenu();
					
					String command = s.nextLine();
					List<Child> children = parent.getChildren();
					
					
					switch (command) {
												
						case "1":	// Add Child
							System.out.println("What is the child's name?");
							String childName = s.nextLine();
							
							Child child = new Child(childName);
							
							// Add child to list of all children
							childUsers.add(child);
							
							// Add child to parent's list of children
							parent.addChild(child);
							
							break;
									
						case "2":	// Edit Child
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to edit:\n");
							
							// Lists all children with ID, name, and mode
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
									
									System.out.println("Set the child's new name: ");
									String newName = s.nextLine();
									
									parent.editChild(c, newName);															
								}
							}
							break;
						
						case "3":	// Delete Child
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to delete:\n");
							
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId2 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId2 == c.getUserId()) {
									parent.removeChild(c);																
								}
							}
							
							break;
							
						case "4":	// Add Token
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot apply tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to add tokens to:\n");
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId3 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId3 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
								
							
									System.out.println("How many tokens would you like to add?");
									
									int tokenAmount = s.nextInt();
									s.nextLine();
									
									System.out.println("Add a note to the token(s) being added.");
									
									String note = s.nextLine();
									
									c.addToken(tokenAmount, note);
								}
							}
									
							break;
						
						case "5":	// Edit Token
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot edit tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to edit tokens for:\n");
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId4 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId4 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
							
									System.out.println("ENTER the TOKEN ID of the token you wish to edit: \n");
									List<Token> tokens = c.getTokens();
									
									for (Token t: tokens) {
										System.out.println("Token ID: " + t.getTokenId() +
												"\tType: " + t.getType() + 
												"\tTimestamp: " + t.getTimestamp() +
												"\tNote: " + t.getNote());
									}
									
									int tokenId = s.nextInt();
									s.nextLine();
									
									for (Token t: tokens) {
										if (tokenId == t.getTokenId()) {
											System.out.println("You have selected:");
											System.out.println("Token ID: " + t.getTokenId() +
													"\tType: " + t.getType() + 
													"\tTimestamp: " + t.getTimestamp() +
													"\tNote: " + t.getNote());
											
											Mode newType;
											
											while (true) {
												System.out.println("Set the token's type: ");
												System.out.println("1. Positive");
												System.out.println("2. Negative");
												String type = s.nextLine();
												
												if (type.equals("1")) {
													newType = Mode.POSITIVE;
													break;
												} else if (type.equals("2")) {
													newType = Mode.NEGATIVE;
													break;
												} else {
													System.out.println("Invalid command.");
												}
											}
											
											System.out.println("Set the token's note: ");
											String newNote = s.nextLine();
											
											c.editToken(t, newType, newNote);		
										}
									}
								}
							}
									
							break;
						
						case "6":	// Delete Token
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot delete tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to edit tokens for:\n");
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId5 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId5 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
							
									System.out.println("ENTER the TOKEN ID of the token you wish to delete: \n");
									List<Token> tokens = c.getTokens();
									
									for (Token t: tokens) {
										System.out.println("Token ID: " + t.getTokenId() +
												"\tType: " + t.getType() + 
												"\tTimestamp: " + t.getTimestamp() +
												"\tNote: " + t.getNote());
									}
									
									int tokenId = s.nextInt();
									s.nextLine();
									
									for (Token t: tokens) {
										if (tokenId == t.getTokenId()) {
											c.removeToken(t);
										}
									}
								}
							}
							break;
						
						case "7":	// View Children Status
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
								List<Token> tokens = c.getTokens();
								
								System.out.println("List of Tokens: ");
								for(Token t: tokens) {
									System.out.println("Token ID: " + t.getTokenId() +
											"\tType: " + t.getType() + 
											"\tTimestamp: " + t.getTimestamp() +
											"\tNote: " + t.getNote());
								}
								
							}
							
							break;
							
						
						case "8":	// Redeem Child's Tokens
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot redeem tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to redeem tokens for:\n");
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId6 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId6 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
									
									List<Token> tokens = c.getTokens();
									
									if (c.getMode().equals(Mode.POSITIVE)) {
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
												
												int tokenCount = 0;
												
												Iterator<Token> iter = tokens.iterator();
												while(iter.hasNext()) {
													if (iter.next().getType().equals(Mode.POSITIVE) && tokenAmount > 0) {
														iter.remove();
														tokenAmount--;
														tokenCount++;
													}
												}
												
												System.out.println(reward.toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
												break;
												
											} else if (redeem.equals("2")) {
												break;
											} else {
												System.out.println("Invalid command.");
											}
										}
										
									} else if (c.getMode().equals(Mode.NEGATIVE)) {
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
												
												int tokenCount = 0;
												
												Iterator<Token> iter = tokens.iterator();
												while(iter.hasNext()) {
													if (iter.next().getType().equals(Mode.NEGATIVE) && tokenAmount > 0) {
														iter.remove();
														tokenAmount--;
														tokenCount++;
													}
												}
												
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
							}
						
						case "9":	// Token Redemption Settings							
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot set redemption rewards.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to set redemption rewards for:\n");
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId7 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId7 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
									System.out.println("What is the cost to redeem the reward? (Duplicate values will overwrite reward)");
									int tokenAmount = s.nextInt();
									s.nextLine();
									
									System.out.println("What is the reward?");
									String reward = s.nextLine();
									
									c.addRewards(tokenAmount, reward);		
								}
							}
							break;
						
						case "10":	// Set Child Mode
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot set redemption rewards.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to set redemption rewards for:\n");
							
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId8 = s.nextInt();
							s.nextLine();		
							
							for (Child c: children) {
								if (childId8 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
									
									Mode newMode;
									
									while (true) {
										System.out.println("Set the child's mode: ");
										System.out.println("1. Positive");
										System.out.println("2. Negative");
										String type = s.nextLine();
										
										if (type.equals("1")) {
											newMode = Mode.POSITIVE;
											break;
										} else if (type.equals("2")) {
											newMode = Mode.NEGATIVE;
											break;
										} else {
											System.out.println("Invalid command.");
										}
									}
									
									c.setMode(newMode);
								}
							}
						
						case "11":	// Schedule Tokens	
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot schedule tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to set redemption rewards for:\n");
							
							for (Child c: children) {
								System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
										"\tChild's Name: " + c.getName() + 
										"\tChild's Mode: " + c.getMode() + "\n");
							}
							
							int childId9 = s.nextInt();
							s.nextLine();
	
							for (Child c: children) {
								if (childId9 == c.getUserId()) {
									System.out.println("You have selected:");
									System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
											"\tChild's Name: " + c.getName() + 
											"\tChild's Mode: " + c.getMode() + "\n");
									System.out.println("");
									
									if (c.getReset()) {
										System.out.println("The schedule for this child has been reset.");
										c.setLock(true);
										c.setReset(false);
									}
									
									System.out.println("Set the time interval (in seconds) between adding tokens: ");
									int timeInterval = s.nextInt();
									s.nextLine();
									
									System.out.println("Set how many tokens should be added after each interval: ");
									int tokenAmount = s.nextInt();
									s.nextLine();
									
									c.scheduleToken(timeInterval, tokenAmount);
	
								}
							}
							
						case "0":	// EXIT
							parentMenu = false;
							break;
							
						default:
							continue;
							
					}
				}
			} else if (selection.equals("C")) {
				if (childUsers.isEmpty()) {
					System.out.println("Parents haven't added any kids yet!");
					break;
				}
	
				System.out.println("Type in your ID number: \n");
				
				for (Child c: childUsers) {
					System.out.println("User ID: " + Integer.toString(c.getUserId()) + 
							"\tChild's Name: " + c.getName() + 
							"\tChild's Mode: " + c.getMode() + "\n");
				}
				
				int childId = s.nextInt();
				s.nextLine();

				for (Child c: childUsers) {
					if (childId == c.getUserId()) {
						System.out.println("Hello, " + c.getName() + ", type a number to choose!");
						childMenu = true;
						
						while(childMenu) {
							displayChildMenu();
						
							String command = s.nextLine();
							List<Token> tokens = c.getTokens();

							switch (command) {
								
								case "1":	// List Tokens	
									
									System.out.println("List of your Tokens: ");
									for(Token t: tokens) {
										System.out.println("Token ID: " + t.getTokenId() +
												"\tType: " + t.getType() + 
												"\tTimestamp: " + t.getTimestamp() +
												"\tNote: " + t.getNote());
									}
									break;
								
								case "2":	// Redeem Tokens for Reward
									Map<Integer, String> rewards = c.getRewards();
									
									if(c.getMode().equals(Mode.NEGATIVE)) {
										System.out.println("Uh oh, looks like you are in negative mode!");
										break;
									} else if (rewards.isEmpty()) {
										System.out.println("Uh oh, looks like your parent set no rewards!");
										break;
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
											
											while (true) {
												System.out.println("For how many tokens? (You currently have " + tokensOwned + " tokens.)");
												tokenAmount = s.nextInt();
												s.nextLine();
												
												if (tokenAmount > tokensOwned) {
													System.out.println("You don't have enough tokens!");
													tokenAmount = 0;
												}
											
											
												for (Map.Entry<Integer, String> entry : rewards.entrySet()) {
													if(entry.getKey() == tokenAmount) {
														while(true) {
															System.out.println("Are you sure you want to redeem " + entry.getValue() + " for " +
																	entry.getKey() + " tokens?");
															System.out.println("1. Yes");
															System.out.println("2. No");
															
															String confirm = s.nextLine();
															
															if (confirm.equals("1")) {
	
																c.redeemTokens(tokenAmount);
																
																System.out.println(entry.getValue().toUpperCase() + " has been redeemed for " + entry.getKey() + " tokens.");
																break;
															} else if (confirm.equals("2")) {
																break;
															} else {
																System.out.println("Invalid command.");
															}
														}
													}
												}
												break;
											} 
										} else if (redeem.equals("2")) {
											break;
										} else {
											System.out.println("Invalid command.");
										}
										break;
									}
									break;
								case "0":
									childMenu = false;
									break;
							}
						}
					}
				}
			}
		}
		s.close();
	}
	
	public void displayParentMenu() {
		System.out.println("----------");
		System.out.println("1: Add Child");
		System.out.println("2: Edit Child");
		System.out.println("3: Delete Child");
		System.out.println("4: Add Token");
		System.out.println("5: Edit Token");
		System.out.println("6: Delete Token");
		System.out.println("7: View Children Status");
		System.out.println("8: Redeem Child's Tokens");
		System.out.println("9: Token Redemption Settings");
		System.out.println("10: Set Child Mode");
		System.out.println("11: Schedule Tokens");
		System.out.println();
		System.out.println("0: Exit");
		System.out.println("----------");
	}
	
	public void displayChildMenu() {
		System.out.println("----------");
		System.out.println("1: View Tokens");
		System.out.println("2: Redeem Reward");
		System.out.println();
		System.out.println("0: Exit");
		System.out.println("----------");
	}

}
