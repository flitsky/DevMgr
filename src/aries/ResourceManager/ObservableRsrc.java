package aries.ResourceManager;

import java.util.Observable;

public class ObservableRsrc extends Observable {
	private String resourceId = "";

	public ObservableRsrc(String rsrcId) {
		this.resourceId = rsrcId;
	}

	private void setResourceID(String rsrcId) {
		this.resourceId = rsrcId;
	}

	public String getResourceID() {
		return resourceId;
	}

	private void onChanged(String rsrcId) {
		if (this.resourceId == rsrcId) {
			setChanged();
			notifyObservers();
		}
	}
}
