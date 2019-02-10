package org.zique.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.zique.messenger.model.Message;

/**
 * TODO Implement later
 * @author Zique Yuutaka
 *
 */
public class DatabaseClass {
	
	private static Map<Long, Message> messages = new HashMap<Long, Message>();
	
	//Debugging method
/*	public static Map<Long, Message> getMessages(){
		Message m1 = new Message(1L, "Hello", "Dude");
		Message m2 = new Message(2L, "World", "Girl");
		
		messages.put((long)1+messages.size(), m1);
		messages.put((long)1+messages.size()+1, m2);
		return messages;
	}*/
	
	public static Map<Long, Message> getMessages(){
		return messages;
	}

}
