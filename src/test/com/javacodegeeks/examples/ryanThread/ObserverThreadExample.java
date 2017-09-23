package test.com.javacodegeeks.examples.ryanThread;

public class ObserverThreadExample {
	static ObservableMsg observableMsg = new ObservableMsg(null);
	static String cmd1 = "post";
	static String cmd2 = "get";

	public static void main(String[] args) {
		//Thread processCommand = new Thread(new ProcessCommand(queueReq, queueResp));
		Thread addObservableThread = new Thread(new AddObservableThread(observableMsg));
		
		addObservableThread.start();
		
		processCommand();
	}
	
	static void processCommand() {
		
		if(cmd1.equals("post")) {
			ObserverSignUp post = new ObserverSignUp();
			observableMsg.addObserver(post);
			cmd1 = "";
		}

		if(cmd2.equals("get")) {
			ObserverGet get = new ObserverGet();
			observableMsg.addObserver(get);
			cmd2 = "";
		}
	}
}
