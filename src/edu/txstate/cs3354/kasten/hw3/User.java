package edu.txstate.cs3354.kasten.hw3;

public class User {
	private static int counter = 0;
	
	private String name;
	private int UID;
	
	
	public User(String userName) {
		name = userName;
		UID = ++counter;
	}

	public String getName() {
		return name;
	}
	
	public Integer getUserID() {
		return UID;
	}
}
