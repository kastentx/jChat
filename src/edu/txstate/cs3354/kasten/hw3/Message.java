package edu.txstate.cs3354.kasten.hw3;

public class Message {
	private String sentBy;
	private String textContent;
	
	public Message(User sender, String body) {
		sentBy = sender.getName();
		textContent = body;
	}
	
	public String getSender() {
		return sentBy;
	}

	public String getTextContent() {
		return textContent;
	}	
}
