package aries.MessageProcess;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import aries.DeviceManager.Message;

public class ObservableRespMsg extends Observable {
	private String msg = "";
	private String messageId = "";
	private BlockingQueue<Message> queueResp;
	private Timer expiredTimer;

	public ObservableRespMsg(BlockingQueue<Message> qResp) {
		this.queueResp = qResp;
	}

	private void setMessage(String str) {
		this.msg = str;
		setChanged();
		notifyObservers();
	}

	public String getMessage() {
		return msg;
	}

	private void setMessageId(String msgId) {
		this.messageId = msgId;
		ScheduledJob job = new ScheduledJob();
		expiredTimer = new Timer();
		expiredTimer.schedule(job, 2000);
	}

	public String getMessageId() {
		return messageId;
	}

	public void work() {
		if (this.msg == "") {
			takeMessage();
		}
	}

	public boolean takeMessage() {
		Message qResp;

		// initialization by take message.
		this.msg = "";
		if(expiredTimer != null)
			expiredTimer.cancel();

		// take message & check response queue is available
		if (queueResp.isEmpty()) {
		} else {
			try {
				if ((qResp = queueResp.take()).getMsg() != "") {
					setMessageId(qResp.getMessageID());
					setMessage(qResp.getMsg());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	class ScheduledJob extends TimerTask {

		public void run() {
			System.out.println("### No one take the response message");
			takeMessage();
		}
	}
}
