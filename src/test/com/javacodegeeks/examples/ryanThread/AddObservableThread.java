package test.com.javacodegeeks.examples.ryanThread;

public class AddObservableThread implements Runnable {
	static ObservableMsg observableMsg;
	
	AddObservableThread(ObservableMsg obsMsg) {
		AddObservableThread.observableMsg = obsMsg;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			observableMsg.setMessage("  New msg arrived... ");
			Thread.sleep(1000);
			observableMsg.setMessage("  Second msg arrived... ");
			Thread.sleep(2000);
			observableMsg.setMessage("  Common msg arrived... ");
			Thread.sleep(2000);
			observableMsg.setMessage("  Common 222 msg arrived... ");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
