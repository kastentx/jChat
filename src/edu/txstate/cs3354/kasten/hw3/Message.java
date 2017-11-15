package edu.txstate.cs3354.kasten.hw3;

/**
 * The Message class represents a message sent by a user of a chat app
 * and stores the name of the sender and it's text content.
 * 
 * @author nkasten
 */
public class Message {
	private String sentBy;
	private String textContent;
	
	/**
	 * Creates a new Message object.
	 * 
	 * @param sender the User sending this message
	 * @param body the text content of this message
	 */
	public Message(User sender, String body) {
		sentBy = sender.getName();
		textContent = body;
	}
	
	/**
	 * Public getter for this Message's sender
	 * 
	 * @return the name of this Message's sender
	 */
	public String getSender() {
		return sentBy;
	}

	/**
	 * Public getter for this Message's body
	 * 
	 * @return the text content of this Message
	 */
	public String getTextContent() {
		return textContent;
	}	
}
