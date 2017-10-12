package aries.ResourceManager;

import java.util.HashMap;
import java.util.Observer;

public class RsrcMgr {

	private static class Singleton {
		private static final RsrcMgr instance = new RsrcMgr();
	}

	public static RsrcMgr getInstance() {
		return Singleton.instance;
	}

	static HashMap<String, ObservableRsrc> rsrcIdObservableMap = new HashMap<String, ObservableRsrc>();
	static int iVal = 0;

	public boolean Subscribe(String[] rsrcIds, Observer subscriber) {
		for (String rsrcId : rsrcIds) {
			Subscribe(rsrcId, subscriber);
		}
		return true;
	}

	public boolean Subscribe(String rsrcId, Observer subscriber) {
		if (!rsrcIdObservableMap.containsKey(rsrcId)) {
			System.out.println("new rsrcId ---> put into the Map. id : " + rsrcId);
			rsrcIdObservableMap.put(rsrcId, new ObservableRsrc(rsrcId));
		}
		rsrcIdObservableMap.get(rsrcId).addObserver(subscriber);
		return true;
	}

	public boolean Unsubscribe(String[] rsrcIds, Observer subscriber) {
		for (String rsrcId : rsrcIds) {
			Unsubscribe(rsrcId, subscriber);
		}
		return true;
	}

	public boolean Unsubscribe(String rsrcId, Observer subscriber) {
		if (!rsrcIdObservableMap.containsKey(rsrcId)) {
			// error?
			return false;
		}
		// rsrcIdObservableMap.get(rsrcId).deleteObserver(subscriber);
		ObservableRsrc obsRsrc = rsrcIdObservableMap.get(rsrcId);
		obsRsrc.deleteObserver(subscriber);
		if (obsRsrc.countObservers() == 0) {
			rsrcIdObservableMap.remove(rsrcId);
			System.out.println("Unsubscribe ~~ countObservers is zero ---> remove from Map. id : " + rsrcId);
		}
		return true;
	}

	public boolean onChanged(String[] rsrcIds) {
		for (String rsrcId : rsrcIds) {
			onChanged(rsrcId);
		}
		return true;
	}

	public boolean onChanged(String rsrcId) {
		if (rsrcIdObservableMap.containsKey(rsrcId)) {
			ObservableRsrc obs = rsrcIdObservableMap.get(rsrcId);
			obs.onChanged(rsrcId);
		}
		return true;
	}

	public boolean SingletonTest() {
		System.out.println("SingletonTest [[[ iVal=" + iVal++);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("SingletonTest ]]] iVal=" + iVal++);
		return true;
	}
}
