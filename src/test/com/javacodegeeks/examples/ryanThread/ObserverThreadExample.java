package test.com.javacodegeeks.examples.ryanThread;

public class ObserverThreadExample {
	static ObservableMsg observableMsg = new ObservableMsg(null);

	public static void main(String[] args) {
		//Thread processCommand = new Thread(new ProcessCommand(queueReq, queueResp));
		Thread addObservableThread = new Thread(new AddObservableThread(observableMsg));

		ObserverThread1 observer1 = new ObserverThread1();
		ObserverThread2 observer2 = new ObserverThread2();

		observableMsg.addObserver(observer1);
		observableMsg.addObserver(observer2);

		addObservableThread.start();
	}
}
