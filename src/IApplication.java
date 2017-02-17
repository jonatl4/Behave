
public abstract class IApplication {
	abstract void init();
	abstract void start();
	abstract void stop();
	
	// template method
	public final void run() {
		
		// initiate welcome
		init();
		
		// start the application, main menu
		start();
		
		// end the application, close
		stop();
	}
	
}
