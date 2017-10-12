package aries.TriggerManager;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import aries.ResourceManager.ObservableRsrc;
import aries.ResourceManager.RsrcMgr;

public class Trigger implements Observer {
	private String ResourceIDs[];

	public Trigger(String rsrcID) {
		this.ResourceIDs = new String[1];
		this.ResourceIDs[0] = rsrcID;
		subscribe(rsrcID);
	}

	public Trigger(String rsrcIDs[]) {
		this.ResourceIDs = rsrcIDs;
		subscribe(rsrcIDs);
	}

	@Override
	public void update(Observable observable, Object arg) {
		String str = ((ObservableRsrc) observable).getResourceID();
		System.out.println("Trigger Observer update()... ResourceIDs=" + Arrays.toString(ResourceIDs) + str);
	}

	public String[] getRsrcIDs() {
		return ResourceIDs;
	}
	
	public boolean deleteTrigger() {
		unsubscribe(this.ResourceIDs);
		this.ResourceIDs = null;
		return true;
	}

	private void subscribe(String rsrcID) {
		RsrcMgr.getInstance().Subscribe(rsrcID, this);
	}

	private void subscribe(String[] rsrcIDs) {
		for (String rsrcID : rsrcIDs) {
			subscribe(rsrcID);
		}
	}

	private void unsubscribe(String rsrcID) {
		RsrcMgr.getInstance().Unsubscribe(rsrcID, this);
	}

	private void unsubscribe(String[] rsrcIDs) {
		for (String rsrcID : rsrcIDs) {
			unsubscribe(rsrcID);
		}
	}
}
