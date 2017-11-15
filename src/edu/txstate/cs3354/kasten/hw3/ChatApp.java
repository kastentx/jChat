package edu.txstate.cs3354.kasten.hw3;

import java.util.ArrayList;
import java.util.Observable;

public class ChatApp extends Observable {
	private ArrayList<Message> chatMessages = new ArrayList<>();
	private int userCount = 0;
 
	public ChatApp(String... users) {
		userCount = users.length;
		for (int i = 0; i< users.length; i++) {
			addObserver(new ChatWindow(new User(users[i]), this));
		}
	}
	
	public void addMessage(Message newMessage) {
		// update our internal state by adding new message 
		chatMessages.add(newMessage);
		
		// set this object as changed
		setChanged();
		
		// trigger notification of all observers (clearing 'changed' flag)
		notifyObservers(newMessage);
	}
	
	public String getUserCount() {
		return new Integer(userCount).toString();
	}
}
