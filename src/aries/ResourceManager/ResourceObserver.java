package aries.ResourceManager;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

public abstract class ResourceObserver implements Observer {
	private String ResourceID = "";

	public ResourceObserver(String rsrcID) {
		this.ResourceID = rsrcID;
	}

	@Override
	public void update(Observable observable, Object arg) {
	}

	public String getRsrcID() {
		return ResourceID;
	}

	protected void RemoveObserver() {
	}

	protected abstract void recvdReqProc(JSONObject receivedRequest);

}
