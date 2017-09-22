package test.com.javacodegeeks.examples.ryanThread;

import java.util.Observable;
import java.util.Observer;

public class ObserverThread1 implements Observer {

	private ObservableMsg msgUpdate;

	public ObserverThread1() {
		System.out.println("ObserverThread1 [command processing][make message][send req/resp] ~> [wait resp]");
	}

	@Override
	public void update(Observable observable, Object arg) {
		msgUpdate = (ObservableMsg) observable;
		if (msgUpdate.getMessage().contains("New msg"))
			System.out.println("ObserverThread1 " + msgUpdate.getMessage());
		else if (msgUpdate.getMessage().contains("Common")) {
			System.out.println("ObserverThread1 " + msgUpdate.getMessage());
			System.out.println("ObserverThread1 - process complete!!!   deleteObserver ");
			msgUpdate.deleteObserver(this);
		}
	}
}
