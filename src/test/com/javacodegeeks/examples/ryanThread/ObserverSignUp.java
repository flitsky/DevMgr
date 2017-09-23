package test.com.javacodegeeks.examples.ryanThread;

import java.util.Observable;
import java.util.Observer;

public class ObserverSignUp implements Observer {

	private ObservableMsg msgUpdate;

	ObserverSignUp() {
		System.out.println("ObserverSignUp [command processing][make message][send req/resp] ~> ");
		System.out.println("ObserverSignUp [wait resp...]");
	}

	ObserverSignUp(Observable observable) {
		this();
		observable.addObserver(this);
	}

	@Override
	public void update(Observable observable, Object arg) {
		msgUpdate = (ObservableMsg) observable;
		if (msgUpdate.getMessage().contains("New msg"))
			System.out.println("ObserverThread1 " + msgUpdate.getMessage());
		else if (msgUpdate.getMessage().contains("Common")) {
			System.out.println("ObserverThread1 " + msgUpdate.getMessage());
			System.out.println("ObserverThread1 - process complete!!!   deleteObserver ");
			//msgUpdate.deleteObserver(this);

			//if process done well
			observable.deleteObserver(this);
		}
	}
}
