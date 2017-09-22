package test.com.javacodegeeks.examples.ryanThread;

public class AddObservableThread implements Runnable {
	static ObservableMsg observableMsg;
	
	AddObservableThread(ObservableMsg obsMsg) {
		AddObservableThread.observableMsg = obsMsg;
	}
	@Override
	public void run() {
		observableMsg.setMessage("  New msg arrived... ");
		observableMsg.setMessage("  Second msg arrived... ");
		observableMsg.setMessage("  Common msg arrived... ");
	}

}
