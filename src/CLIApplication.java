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
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId == c.getUserId()) {
									
									// Lists selected child info with ID, name, and mode
									System.out.println("You have selected:");
									c.printChildInfo();
									
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
							for (Child c : children) {
								c.printChildInfo();
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
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId3 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId3 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
									
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
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId4 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId4 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
							
									System.out.println("ENTER the TOKEN ID of the token you wish to edit: \n");
									List<Token> tokens = c.getTokens();
									
									// Lists all tokens w/ type, timestamp, and note
									for (Token t : tokens) {
										t.printTokenInfo();
									}
									
									int tokenId = s.nextInt();
									s.nextLine();
									
									for (Token t: tokens) {
										if (tokenId == t.getTokenId()) {
											System.out.println("You have selected:");
											t.printTokenInfo();
											
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
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId5 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId5 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
							
									System.out.println("ENTER the TOKEN ID of the token you wish to delete: \n");
									List<Token> tokens = c.getTokens();
									for (Token t : tokens) {
										t.printTokenInfo();
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
								
								if(tokens.isEmpty()) {
									System.out.println("No tokens available.");
								} else {									
									System.out.println("List of Tokens: ");
									for(Token t: tokens) {
										t.printTokenInfo();
									}
								}
							}
							break;
							
						
						case "8":	// Redeem Child's Tokens
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot redeem tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to redeem tokens for:\n");
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId6 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId6 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
									
									c.redeemTokensByParent(c);
									
								}
							}
							break;
						
						case "9":	// Token Redemption Settings							
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot set redemption rewards.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to set redemption rewards for:\n");
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId7 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId7 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
									
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
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId8 = s.nextInt();
							s.nextLine();		
							
							for (Child c: children) {
								if (childId8 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
									
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
							break;
						
						case "11":	// Schedule Tokens	
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot schedule tokens.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to set redemption rewards for:\n");
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId9 = s.nextInt();
							s.nextLine();
	
							for (Child c: children) {
								if (childId9 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
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
									if(tokens.isEmpty()) {
										System.out.println("No tokens available.");
									} else {									
										System.out.println("List of your Tokens: ");
										for(Token t: tokens) {
											t.printTokenInfo();
										}
									}
									break;
								
								case "2":	// Redeem Tokens for Reward
									c.redeemTokensByChild(c);
									break;
								case "0":
									childMenu = false;
									break;
								default:
									continue;
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
