package edu.txstate.cs3354.kasten.hw3;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class ChatWindow extends JFrame implements ActionListener, Observer {
	private User myUser;
	private JTextArea inputField;
	private JTextArea outputDisplay;
	private JButton sendButton;
	private ChatApp myChat;
	private JScrollPane inputScroll;
	private JScrollPane outputScroll;
	
	// TODO may need to create a new class that extends observable
	// which includes both the inputField and the sendButton... (UserInput?)
	// this way the internal state of the object can store the last 
	// message that has been "sent", which gets appended to the 
	// main conversation, which should live in the chat app?
	
	// the above means that the main chat app has to observe the
	// input class.. i think?
	
	// this would also mean that the output display window would
	// need to observe the main chat... this could probably
	// be done through implementing the observer interface on the
	// currently existing chatWindow class
	
	public ChatWindow(User aUser, ChatApp appInstance) {
		myChat = appInstance;
		myUser = aUser; 
		int initialPos = new Integer(myUser.getUserID()) % 3;
		int heightOffset = (int)Math.ceil(
				(double)myUser.getUserID() / 3) * 50;
		this.setTitle(myUser.getName() + 
				" - User #" + 
				myUser.getUserID().toString());
		
		// setting window size and position based on number of users
		this.setSize(400, 500);
		if (initialPos == 0) {
			this.setLocation(865, 100 + heightOffset);
		} else if (initialPos == 1) {
			this.setLocation(15, 100 + heightOffset);
		}  else if (initialPos == 2) {
			this.setLocation(440, 100 + heightOffset);
		}	
		
		// jbutton to use for sending messages
		sendButton = new JButton("Send");
		sendButton.setBounds(320, 388, 65, 65);
		sendButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        // add message to the user's personal sentMessages ArrayList
	    	  	sendMessage(new Message(myUser, inputField.getText()));
	    	  	inputField.setText(" ");
	      }
	    });
		this.add(sendButton);
		
		// textarea for text input w/ scrollbar
		// with label showing users where to enter messages
		JLabel inputLabel = new JLabel("Enter your message below...");
		inputLabel.setBounds(17, 370, 300, 17);
		inputField = new JTextArea();
		inputField.setRows(3);
		inputField.setLineWrap(true);
		
		inputScroll = new JScrollPane(inputField);
		inputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputScroll.setBounds(17, 390, 300, 60);
		this.add(inputLabel);
		this.add(inputScroll);
		
		// textarea for text output w/ scrollbar 
		// with labels for welcome message and user count
		JLabel outputLabel = new JLabel("Hello " + myUser.getName() + ". Welcome to jChat!");
		outputLabel.setBounds(100, 5, 350, 17);
		JLabel userCount = new JLabel("Number of users in chat: " + myChat.getUserCount());
		userCount.setBounds(200, 290, 200, 100);
		outputDisplay = new JTextArea();
		outputDisplay.setEditable(false);
		outputDisplay.setLineWrap(true);
	
		outputScroll = new JScrollPane(outputDisplay);
		outputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		outputScroll.setBounds(25, 25, 350, 300);
		this.add(outputLabel);
		this.add(userCount);
		this.add(outputScroll);
		
		// set window options and make visible
		// after adding components
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action performed: " + e);	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		String messageContent = ((Message) arg).getTextContent().trim();
		String messageUser = ((Message) arg).getSender();
		if (messageUser == myUser.getName()) messageUser = "Me";
		
		//System.out.println("update called on " + myUser.getName() + " with args: " + messageContent);
		outputDisplay.append(messageUser + ": " + messageContent + "\n");
	}
	
	private void sendMessage (Message newMessage) {
		myChat.addMessage(newMessage);
	}
}
