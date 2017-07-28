package com.vishal.restful.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.vishal.restful.messenger.database.Database;
import com.vishal.restful.messenger.exception.DataNotFoundException;
import com.vishal.restful.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = Database.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1, "Hello", "Vishal"));
		messages.put(2L, new Message(2, "Hello Restful", "Vishal"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());

	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesforYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year)
				messagesforYear.add(message);
		}
		return messagesforYear;

	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<>(messages.values());
		if (start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start + size);

	}

	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null)
			throw new DataNotFoundException("Message with id " + id + " not found!");
		return message;
	}

	public Message addMessage(Message msg) {
		msg.setId(messages.size() + 1);
		messages.put(msg.getId(), msg);
		return msg;
	}

	public Message updateMessage(Message msg) {
		if (msg.getId() <= 0) {
			return null;
		}
		messages.put(msg.getId(), msg);
		return msg;
	}

	public Message removeMessage(long id) {

		return messages.remove(id);
	}

}
