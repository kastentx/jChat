package edu.txstate.cs3354.kasten.hw3;

/**
 * The User class represents a user of a chat app. There are attributes
 * containing the user's name as well as a unique ID number.
 * 
 * @author nkasten
 */
public class User {
	private static int counter = 0;
	
	private String name;
	private int UID;
	
	/**
	 * Creates a new User object.
	 * 
	 * @param userName the name of this new user
	 */
	public User(String userName) {
		name = userName;
		UID = ++counter;
	}

	/**
	 * Public getter for this user's name.
	 * 
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Public getter for this user's ID.
	 * 
	 * @return user's UID
	 */
	public Integer getUserID() {
		return UID;
	}
}
