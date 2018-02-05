package managers;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import interfaces.OnPutMessage;
import interfaces.OnRefreshData;

public class MessageHandler{
	private Map<String, Integer>messageQueue;
	private OnRefreshData onRefreshData;
	private OnPutMessage onPutMessage;

	public OnPutMessage getOnPutMessage() {
		return onPutMessage;
	}

	public void setOnPutMessage(OnPutMessage onPutMessage) {
		this.onPutMessage = onPutMessage;
	}

	private MessageHandler(){
		messageQueue = new ConcurrentHashMap<String, Integer>();
	}
	
	public OnRefreshData getOnRefreshData() {
		return onRefreshData;
	}

	public void setOnRefreshData(OnRefreshData onRefreshData) {
		this.onRefreshData = onRefreshData;
	}



	public Map<String, Integer>getMessageQueue(){
		return messageQueue;
	}
	
	public void removeMessageFromQueue(String message){
		//remove
	}
	
	public void refresh(){
		Iterator<Map.Entry<String, Integer>> iter = messageQueue.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry<String,Integer> entry = iter.next();
		    if(entry.getValue()>0){
		        iter.remove();
		    }
		}
	}
	
	public void addMessageToQueue(String message){
		if(onRefreshData!=null){
			onRefreshData.onRefreshData(message);
		}
	}

	
	public static final MessageHandler instance = new MessageHandler();
}