import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.joda.time.DateTime;

public class CLIApplication extends IApplication {
	
	private List<Parent> users;
	
	
	public CLIApplication() {
		users = new ArrayList<Parent>();
	}
	
	public void init() {
		System.out.println(",-----.         ,--.                            ");  
		System.out.println("|  |) /_  ,---. |  ,---.  ,--,--.,--.  ,--.,---.");  
		System.out.println("|  .-.  \\| .-. :|  .-.  |' ,-.  | \\  `'  /| .-. :"); 
		System.out.println("|  '--' /\\   --.|  | |  |\\ '-'  |  \\    / \\   --."); 
		System.out.println("`------'  `----'`--' `--' `--`--'   `--'   `----'"); 
		
		System.out.println("				Welcome to Behave!					\n");
		System.out.println("Note: When using the command line interface, please enlarge the console window for better user experience. Enjoy!");
	}
	
	public void start() {
		
		Scanner s = new Scanner(System.in);
		DateTime startTime = new DateTime();
		
		boolean mainMenu = true;
		
		while(mainMenu) {
			
			displayMainMenu();
			
			String selection = s.nextLine();
			
			boolean parentMenu;
			boolean childMenu;
			Parent parent;
			
			// Selection menu for Parent or Child
			if (selection.toUpperCase().equals("P")) {
				
				// Check if new parent
				if (users.isEmpty()) {
					System.out.println("Welcome parent! What is your name?");
					String parentName = s.nextLine();
					parent = new Parent(parentName);
					users.add(parent);
				} else {
					parent = users.get(0);
					System.out.println("Welcome back, " + parent.getName() + "\n");
				}
				
				parentMenu = true;
			
				while (parentMenu) {
					System.out.println("\n" + parent.getName() + ", select an action:\n");
					displayParentMenu();
					
					String command = s.nextLine();
					List<Child> children = parent.getChildren();
					Utilities u = new Utilities();
					ModeFactory modeFactory = new ModeFactory();
					
					
					switch (command) {
												
						case "1":	// Add Child
							System.out.println("What is the child's name?");
							String childName = s.nextLine();
							
							Child child = new Child(childName);
							
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
									
									System.out.println("Note: Tokens of type " + c.getMode().toString() + " will be added.");
									System.out.println("To change types, please set child to the desired mode.\n");
									
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
													newType = modeFactory.getMode("POSITIVE");
													break;
												} else if (type.equals("2")) {
													newType = modeFactory.getMode("NEGATIVE");
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
										c.removeToken(t);
										break;
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
									
									if (c.getMode() instanceof Positive) {
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
												
												int tokenCount = c.redeemTokens(tokenAmount, modeFactory.getMode("POSITIVE"));
												
												System.out.println(reward.toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
												break;
												
											} else if (redeem.equals("2")) {
												break;
											} else {
												System.out.println("Invalid command.");
											}
										}
										
									} else if (c.getMode() instanceof Negative) {
										while (true) {
											System.out.println("This child's mode is currently NEGATIVE. Redeem a punishment?");
											System.out.println("1. Yes");
											System.out.println("2. No");
											String punishment = s.nextLine();
											
											if (punishment.equals("1")) {
												int tokenAmount = 0;
												while (tokenAmount <= 0) {
													System.out.println("For how many tokens? (Must be greater than 0)");
													tokenAmount = s.nextInt();
													s.nextLine();
												}
												
												System.out.println("For what punishment(any)?");
												String reward = s.nextLine();
												
												int tokenCount = c.redeemTokens(tokenAmount, modeFactory.getMode("NEGATIVE"));

												System.out.println(reward.toUpperCase() + " has been redeemed for " + tokenCount + " tokens.");
												break;
												
											} else if (punishment.equals("2")) {
												break;
											} else {
												System.out.println("Invalid command.");
											}
										}
									}
									
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
											newMode = modeFactory.getMode("POSITIVE");
											break;
										} else if (type.equals("2")) {
											newMode = modeFactory.getMode("NEGATIVE");
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
							
							boolean newSched = true;
	
							for (Child c: children) {
								if (childId9 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
									System.out.println("Note: Tokens of type " + c.getMode().toString() + " will be added.");
									System.out.println("To change types, please set child to the desired mode.\n");
									
									if (c.getReset()) {
										System.out.println("The schedule for this child has been reset.\n");
										c.setLock(true);
										c.setReset(false);
										
										while (true) {
											System.out.println("Would you like to create a new schedule for this child?");
											System.out.println("1. Yes");
											System.out.println("2. No");
											
											
											if(s.nextLine().equals("1")) {
												break;
											} else if (s.nextLine().equals("2")) {
												newSched = false;
												break;
											} else {
												System.out.println("Invalid command.");
											}
												
										};
									}
									
									if (newSched) {	
										System.out.println("Set the time interval (in seconds) between adding tokens: ");
										int timeInterval = s.nextInt();
										s.nextLine();
										
										System.out.println("Set how many tokens should be added after each interval: ");
										int tokenAmount = s.nextInt();
										s.nextLine();
										
										c.scheduleToken(timeInterval, tokenAmount);
									}
	
								}
							}
							break;
							
						case "12":	// View Behavior History of Child
							if (children.isEmpty()) {
								System.out.println("You have yet to add any children, so you cannot view behavior history.");
								break;
							}
							
							System.out.println("Enter the USER ID of the child you wish to view behavior history for: \n");
							for (Child c : children) {
								c.printChildInfo();
							}
							
							int childId10 = s.nextInt();
							s.nextLine();
							
							for (Child c: children) {
								if (childId10 == c.getUserId()) {
									System.out.println("You have selected:");
									c.printChildInfo();
									
									while (true) {
										System.out.println("What token mode would you like to view the histogram in?");
										System.out.println("1. Positive");
										System.out.println("2. Negative");
										String type = s.nextLine();
										
										int intervals = 0;
										while (intervals <= 0) {
											System.out.println("Enter the number of total minutes: (Must be greater than 0)");
											intervals = s.nextInt();
											s.nextLine();
										}
										
										
										if (type.equals("1")) {
											ViewHistory chart = new ViewHistory(c.getName() + "'s Histogram", c, modeFactory.getMode("POSITIVE"), startTime, intervals);
											chart.pack( );
											RefineryUtilities.centerFrameOnScreen( chart );
											chart.setVisible( true );
											break;
										} else if (type.equals("2")) {
											ViewHistory chart = new ViewHistory(c.getName() + "'s Histogram", c, modeFactory.getMode("NEGATIVE"), startTime, intervals);
											chart.pack( );
											RefineryUtilities.centerFrameOnScreen( chart );
											chart.setVisible( true );
											break;
										} else {
											System.out.println("Invalid command.");
										}
									}
								}
							}
							break;
												
						case "E":	// EXIT
							parentMenu = false;
							break;
							
						case "S":	// Save data
							u.saveFile(users);
							break;
							
						case "L":	// Load data	
							users = u.loadFile("myfile");
							parentMenu = false;
							System.out.println("\nYou will be returned to the main menu.");
							break;
							
						default:
							continue;
							
					}
				}
			} else if (selection.toUpperCase().equals("C")) {

				if (users.isEmpty()) {
					System.out.println("Invalid! Parent must create an account.");
					break;
				}
				
				List<Child> childUsers = users.get(0).getChildren();
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
							ModeFactory modeFactory = new ModeFactory();

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
									if(c.getMode() instanceof Negative) {
										System.out.println("Uh oh, looks like you are in negative mode!");
										return;
									} else if (c.getRewards().isEmpty()) {
										System.out.println("Uh oh, looks like your parent set no rewards!");
										return;
									}
									
									System.out.println("Here are some rewards you can use your tokens to redeem!\n");

									for(Map.Entry<Integer, String> entry : c.getRewards().entrySet()) {
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
											
												for (Map.Entry<Integer, String> entry : c.getRewards().entrySet()) {
													if(entry.getKey() == tokenAmount) {
														boolean confirmRedeem = true;
														while(confirmRedeem) {
															System.out.println("Are you sure you want to redeem " + entry.getValue() + " for " +
																	entry.getKey() + " tokens?");
															System.out.println("1. Yes");
															System.out.println("2. No");
															
															String confirm = s.nextLine();
															
															if (confirm.equals("1")) {
																int tokenCount = c.redeemTokens(tokenAmount, modeFactory.getMode("POSITIVE"));
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
											break;
										} else {
											System.out.println("Invalid command.");
										}
									}
								break;
								
								case "3": // View History	
											
									while (true) {
										System.out.println("What token mode would you like to view the histogram in?");
										System.out.println("1. Positive");
										System.out.println("2. Negative");
										String type = s.nextLine();
										
										int intervals = 0;
										while (intervals <= 0) {
											System.out.println("Enter the number of total minutes: (Must be greater than 0)");
											intervals = s.nextInt();
											s.nextLine();
										}
										
										
										if (type.equals("1")) {
											ViewHistory chart = new ViewHistory(c.getName() + "'s Histogram", c, modeFactory.getMode("POSITIVE"), startTime, intervals);
											chart.pack( );
											RefineryUtilities.centerFrameOnScreen( chart );
											chart.setVisible( true );
											break;
										} else if (type.equals("2")) {
											ViewHistory chart = new ViewHistory(c.getName() + "'s Histogram", c, modeFactory.getMode("NEGATIVE"), startTime, intervals);
											chart.pack( );
											RefineryUtilities.centerFrameOnScreen( chart );
											chart.setVisible( true );
											break;
										} else {
											System.out.println("Invalid command.");
										}
									}
									break;
								case "E":
									childMenu = false;
									break;
								default:
									continue;
							}
						}
					}
				}
			} else if (selection.toUpperCase().equals("E")) {
				mainMenu = false;
			}
		}
		s.close();
	}
	
	public void stop() {
		System.out.println("			Thank you for using Behave!				");
		System.out.println("			Hope to see you again soon!				");
	}
	
	public void displayMainMenu() {
		System.out.println("\n==================================");
		System.out.println("            MAIN MENU");
		System.out.println("==================================");
		System.out.println();
		System.out.println("Are you a (P)arent or a (C)hild?");
		System.out.println();
		System.out.println("...or would you like to (E)xit");
	}
	
	public void displayParentMenu() {
		System.out.println("\n==================================");
		System.out.println("           PARENT MENU");
		System.out.println("==================================");
		System.out.println();
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
		System.out.println("12: View Child's Behavior History");
		System.out.println();
		System.out.println("(L)oad Data");
		System.out.println("(S)ave All Data");
		System.out.println();
		System.out.println("(E)xit");
		System.out.println("----------");
	}
	
	public void displayChildMenu() {
		System.out.println("\n==================================");
		System.out.println("            CHILD MENU");
		System.out.println("==================================");
		System.out.println();
		System.out.println("----------");
		System.out.println("1: View Tokens");
		System.out.println("2: Redeem Reward");
		System.out.println();
		System.out.println("...or would you like to (E)xit");
		System.out.println("----------");
	}

}
