package test.com.javacodegeeks.examples.ryanThread;

import java.util.Observable;
import java.util.Observer;

public class ObserverThread2 implements Observer {

	private ObservableMsg msgUpdate;

	@Override
	public void update(Observable observable, Object arg) {
		msgUpdate = (ObservableMsg) observable;
		if(msgUpdate.getMessage().contains("Second msg") || msgUpdate.getMessage().contains("Common"))
			System.out.println("ObserverThread2 " + msgUpdate.getMessage());
	}
}
