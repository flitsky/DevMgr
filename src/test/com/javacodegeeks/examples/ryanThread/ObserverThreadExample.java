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
			ObserverThread1 post = new ObserverThread1();
			observableMsg.addObserver(post);
			cmd1 = "";
		}

		if(cmd2.equals("get")) {
			ObserverThread2 get = new ObserverThread2();
			observableMsg.addObserver(get);
			cmd2 = "";
		}
	}
}
