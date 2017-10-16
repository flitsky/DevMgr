package io.aries.TriggerManager;

import java.util.HashMap;

public class TrgMgr {

	private static class Singleton {
		private static final TrgMgr instance = new TrgMgr();
	}

	public static TrgMgr getInstance() {
		return Singleton.instance;
	}

	static HashMap<String, Trigger> triggerMap = new HashMap<String, Trigger>();

	public boolean addTrigger(String triggerName, String[] rsrcIDs) {
		if (triggerMap.containsKey(triggerName)) {
			System.out.println("triggerName already exist!!!");
			return false;
		}
		triggerMap.put(triggerName, new Trigger(rsrcIDs));
		return true;
	}

	public boolean addTrigger(String triggerName, String rsrcID) {
		if (triggerMap.containsKey(triggerName)) {
			System.out.println("triggerName already exist!!!");
			return false;
		}
		triggerMap.put(triggerName, new Trigger(rsrcID));
		return true;
	}

	public boolean deleteTrigger(String triggerName) {
		if (!triggerMap.containsKey(triggerName)) {
			// error
			System.out.println("deleteTrigger failed cause triggerName was not contained in the map : " + triggerName);
			return false;
		}
		triggerMap.get(triggerName).deleteTrigger();
		triggerMap.remove(triggerName);
		System.out.println("Trigger deleted... triggerName : " + triggerName);
		return true;
	}
}
