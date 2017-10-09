package aries.ResourceManager;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class RsrcMgr {

	private RsrcMgr() {
	}

	private static class Singleton {
		private static final RsrcMgr instance = new RsrcMgr();
	}

	public static RsrcMgr getInstance() {
		return Singleton.instance;
	}

	static HashMap<String, Observable> rsrcIdObservableMap = new HashMap<String, Observable>();

	public static boolean Subscribe(String[] rsrcIds, Observer subscriber) {
		for (String rsrcId : rsrcIds) {
			Subscribe(rsrcId, subscriber);
		}
		return true;
	}

	public static boolean Subscribe(String rsrcId, Observer subscriber) {
		if (!rsrcIdObservableMap.containsKey(rsrcId)) {
			rsrcIdObservableMap.put(rsrcId, new Observable());
		}
		rsrcIdObservableMap.get(rsrcId).addObserver(subscriber);
		return true;
	}
	
	public static boolean SingletonTest() {
		
		return true;
	}
}
