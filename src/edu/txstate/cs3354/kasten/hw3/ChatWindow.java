package edu.txstate.cs3354.kasten.hw3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * The ChatWindow class acts as our view and implements the Observer
 * interface. It will change when notified by the Observable ChatApp
 * class. Each window contains several graphic elements to act as an
 * interface, allowing a user to participate in a chat with other
 * users.
 * 
 * @author nkasten
 */
public class ChatWindow extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private User myUser;
	private JLabel userCount;
	private JTextArea inputField;
	private JTextArea outputDisplay;
	private JButton sendButton;
	private ChatApp myChat;
	private JScrollPane inputScroll;
	private JScrollPane outputScroll;
	
   /**
    * Creates a new ChatWindow object.
    * 
    * @param aUser User to associate with the window  
    * @param appInstance a reference to the parent chatApp instance
    */
	public ChatWindow(User aUser, ChatApp appInstance) {
		myChat = appInstance;
		myUser = aUser; 
		int initialPos = new Integer(myUser.getUserID()) % 3;
		int heightOffset = (int)Math.ceil(
				(double)myUser.getUserID() / 3) * 50;
		this.setTitle(myUser.getName() + 
				" - User #" + myUser.getUserID().toString());
		
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
	    	  	sendMessage(new Message(myUser, inputField.getText()));
	    	  	inputField.setText(" ");
	      }
	    });
		this.add(sendButton);
		
		// textarea for text input w/ scrollbar
		JLabel inputLabel = new JLabel("Enter your message below...");
		inputLabel.setBounds(17, 370, 300, 17);
		inputField = new JTextArea();
		inputField.setRows(3);
		inputField.setLineWrap(true);
		inputScroll = new JScrollPane(inputField);
		inputScroll.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputScroll.setBounds(17, 390, 300, 60);
		this.add(inputLabel);
		this.add(inputScroll);
		
		// textarea for text output w/ scrollbar
		JLabel outputLabel = new JLabel(
				"Hello " + myUser.getName() + ". Welcome to jChat!");
		outputLabel.setBounds(100, 5, 350, 17);
		userCount = new JLabel(updateUserCount(myChat.getUserCount()));
		userCount.setBounds(200, 330, 200, 20);
		outputDisplay = new JTextArea();
		outputDisplay.setEditable(false);
		outputDisplay.setLineWrap(true);
		outputScroll = new JScrollPane(outputDisplay);
		outputScroll.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		outputScroll.setBounds(25, 25, 350, 300);
		this.add(outputLabel);
		this.add(userCount);
		this.add(outputScroll);
		
		// set window options and make visible
		// after adding components
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
		
		// send a message to other users when a user has left the chat
		ChatWindow myWindow = this;
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		sendMessage(new Message(myUser, " has left the chat..."));
		    		myChat.removeUser(myWindow);
		    }
		});
	}
	
   /**
    * Sends a new message from this window's user to the conversation.
    * 
    * @param newMessage the Message being sent.
    */
	private void sendMessage (Message newMessage) {
		myChat.addMessage(newMessage);
	}
	
	/**
	 * Updates this ChatWindow (the View) with the updated state from the ChatApp.
	 * This can either be an updated userCount or an updated conversation.
	 * 
	 *  @param o the Observable object - the ChatApp
	 *  @param arg the new data from the ChatApp
	 */
	@Override
	public void update(Observable o, Object arg) {
		// check for type of update from the ChatApp
		if (arg instanceof Integer) {
			userCount.setText(updateUserCount((Integer) arg));
		} else if (arg instanceof Message) {
			String messageContent = ((Message) arg).getTextContent().trim();
			String messageUser = ((Message) arg).getSender();
			// properly notate messages sent by user
			if (messageUser == myUser.getName()) messageUser = "Me";			
			// don't display blank messages
			if (messageContent.length() != 0) {
				outputDisplay.append(messageUser + ": " + messageContent + "\n");	
			}
		}
	}
	
	/**
	 * Creates a string that will reflect the updated user count. 
	 * 
	 * @param count the count to be displayed in the label
	 * @return a String to be displayed in the userCount JLabel 
	 */
	public String updateUserCount(Integer count) {
		return "Number of users in chat: " + count.toString();
	}
	       
}
