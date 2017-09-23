package test.com.javacodegeeks.examples.ryanThread;

import java.util.Observable;
import java.util.Observer;

public class ObserverGet implements Observer {

	private ObservableMsg msgUpdate;

	public ObserverGet() {
		System.out.println("ObserverGet [command processing][make message][send req/resp]");
		System.out.println("ObserverGet [wait resp...]");
	}

	@Override
	public void update(Observable observable, Object arg) {
		msgUpdate = (ObservableMsg) observable;
		if (msgUpdate.getMessage().contains("Second msg") || msgUpdate.getMessage().contains("Common"))
			System.out.println("ObserverThread2 " + msgUpdate.getMessage());
	}
}
