package edu.txstate.cs3354.kasten.hw3;

import java.util.ArrayList;
import java.util.Observable;

/**
 * The ChatApp class is an Observable object in our app
 * that manages the state of our conversation and the number
 * of current users.
 * 
 * @author nkasten
 */
public class ChatApp extends Observable {
	private ArrayList<Message> chatMessages = new ArrayList<>();
	private Integer userCount = 0;
 
	/**
	 * Creates a new ChatApp object.
	 * 
	 * @param users an array of users (of undeterminate length)
	 */
	public ChatApp(String... users) {
		// add all users as observers when app starts up
		userCount = users.length;
		for (int i = 0; i< users.length; i++) {
			addObserver(new ChatWindow(new User(users[i]), this));
		}
	}
	
	/**
	 * Adds a new message to the conversation and notifies all observers. 
	 * 
	 * @param newMessage the Message to be added
	 */
	public void addMessage(Message newMessage) {
		// update our internal state by adding new message 
		chatMessages.add(newMessage);
		
		// flag this object as changed
		setChanged();
		
		// trigger notification of all observers (clearing 'changed' flag)
		notifyObservers(newMessage);
	}
	
	/**
	 * Public getter for the current user count.
	 * 
	 * @return userCount
	 */
	public Integer getUserCount() {
		return userCount;
	}
	
	/**
	 * Handles a user quitting the chat app by removing them as an observer
	 * and decreasing the user count, then notifying all remaining observers.
	 * 
	 * @param userWindow the ChatWindow being closed
	 */
	public void removeUser(ChatWindow userWindow) {
		// remove exiting user from list of observers and reduce count
		deleteObserver(userWindow);
		userCount--;
		
		// flag our state change and notify observers
		setChanged();
		notifyObservers(userCount);
	}
}
