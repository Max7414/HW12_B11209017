package Bulletin;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class Bulletin {

	public static void main(String[] args) {

		File f = new File("data");
		if (!f.exists())
			if (!f.mkdir())
				System.out.println("mk dir error");

		while (true) {
			System.out.println("=== Welcome to Bulletin Board ===");
			System.out.println("1) Leave a Comments");
			System.out.println("2) List all Comments");
			System.out.println("0) Exit");
			System.out.println("Please select a number in [1,2,0]");

			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();

			String name = "";
			Date timestamp = new Date();
			String message = "";

			switch (s) {
			case "1":
				System.out.println("What is your name:");
				name = sc.nextLine();

				System.out.println("Leave your message here:");
				message = sc.nextLine();

				timestamp = new Date();
				Comment comment = new Comment(name, timestamp, message);
				try {
					write(name, timestamp, message, comment);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				System.out.println("Your Comment was Saved");

				break;
			case "2":
				read();
				break;
			case "0":
				sc.close();
				System.exit(0);
				break;
			default:
				System.out.println("輸入錯誤!");
			}
		}
	}

	static void write(String name, Date timestamp, String message, Comment comment) throws ClassNotFoundException {
		mkdir();
		try {
			File f = new File("data" + File.separator);
			String[] fileList = f.list();
			boolean repeat = false;
			for (var i : fileList) {
				if ((comment.name + ".ser").equals(i)) {
					try (FileInputStream fis = new FileInputStream("data" + File.separator + i);
							ObjectInputStream ois = new ObjectInputStream(fis)) {
						Comment c = (Comment) ois.readObject();
						c.message.add(message);
						c.timestamp.add(timestamp);
						FileOutputStream fos = new FileOutputStream("data" + File.separator + name + ".ser");
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(c);
						oos.close();
						fos.close();
						repeat = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (!repeat) {
				FileOutputStream fos = new FileOutputStream("data" + File.separator + name + ".ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(comment);
				oos.close();
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void read() {
		mkdir();
		File f = new File("data" + File.separator);
		String[] fileList = f.list();
		for (var i : fileList) {
			try (FileInputStream fis = new FileInputStream("data" + File.separator + i);
					ObjectInputStream ois = new ObjectInputStream(fis)) {

				Comment comment = (Comment) ois.readObject();

				if (comment.timestamp.size() > 1 && comment.message.size() > 1) {
					for (int j = 0; j < comment.message.size(); j++) {

						System.out.println("**********");
						System.out.println("Name:" + comment.name);
						System.out.println("Date:" + comment.timestamp.get(j));
						System.out.println("Message:");
						System.out.println(comment.message.get(j));
					}
				} else {
					System.out.println("**********");
					System.out.println("Name:" + comment.name);
					System.out.println("Date:" + comment.timestamp.get(0));
					System.out.println("Message:");
					System.out.println(comment.message.get(0));
				}

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	static void mkdir() {
		File f = new File("data");
		if (!f.exists())
			if (!f.mkdir())
				System.out.println("mk dir error");
	}

}