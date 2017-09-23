package aries.MessageProcess;

import java.util.Observer;

import aries.DeviceManager.Message;
import aries.DeviceManager.ObservableRespMsg;

public abstract class CmdProcessObserver implements Observer {
	public String ResponseMsgID = "";

	public CmdProcessObserver(Message msg, ObservableRespMsg observable) {
		// TODO Auto-generated constructor stub
		observable.addObserver(this);
		CmdProc(msg);
	}

	protected abstract void CmdProc(Message msg);
}
