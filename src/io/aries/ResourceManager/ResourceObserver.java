package io.aries.ResourceManager;

import java.util.Observable;
import java.util.Observer;

public class ResourceObserver implements Observer {
	private String ResourceID = "";

	public ResourceObserver(String rsrcID) {
		this.ResourceID = rsrcID;
	}

	@Override
	public void update(Observable observable, Object arg) {
		System.out.println("ResourceObserver update()... ResourceID=" + ResourceID);
	}

	public String getRsrcID() {
		return ResourceID;
	}

	protected void RemoveObserver() {
	}

}
