package org.zique.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zique.messenger.database.DatabaseClass;
import org.zique.messenger.exceptions.DataNotFoundException;
import org.zique.messenger.model.Message;

/**
 * Stub of a possible back end database accessor
 * @author Zique Yuutaka
 *
 */
public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService(){
	}
	
	public List<Message> getAllMessages(){
		System.out.println("Getting all messages...");
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year){
				messagesForYear.add(message);
			}
		}
		
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start+size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	public Message getMessage(long id){
		Message message = messages.get(id);
		if(message == null){
			throw new DataNotFoundException("Message id " + id + " not found...");
		}
		return messages.get(id);
	}
	
	//Hacky and not elegant. not sure why put is not returning the message
	public Message addMessage(Message message){
		long id = messages.size();
		message.setId(id);
		message.setCreated(new Date());
		System.out.println("Adding message with id: " + message.getId());
		messages.put(id, message);
		return message;
	}
	
	public Message updateMessage(Message message){
		return messages.put(message.getId(), message);
	}
	
	public Message removeMessage(long key){
		return messages.remove(key);
	}
}
