package test.com.javacodegeeks.examples.ryanThread;

import java.util.Observable;

public class ObservableMsg extends Observable {
	private String Msg;

	public ObservableMsg(String msg) {
		this.Msg = msg;
	}

	public String getMessage() {
		return Msg;
	}

	public void setMessage(String msg) {
		this.Msg = msg;
		setChanged();
		notifyObservers();
	}

}
