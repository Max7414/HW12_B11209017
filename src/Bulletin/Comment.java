package Bulletin;


import java.util.Date;
import java.util.LinkedList;
import java.io.*;

public class Comment implements Serializable {
	static final long serialVersionUID = 1L;

	String name = "";
	LinkedList<Date> timestamp = new LinkedList<>();
	LinkedList<String> message = new LinkedList<>();

	public Comment(String name, Date date, String message) {
		this.name = name;
		this.timestamp.add(date);
		this.message.add(message);
		
	}
}
