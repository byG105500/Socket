package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import server.exception.ConflictException;
import server.exception.ExistingNickNameException;
import server.exception.NameNotFoundException;

public class AddNicknameQuery extends Query implements Runnable {

	private String nick;

	public AddNicknameQuery(Socket socket, PrintWriter out, BufferedReader in,
			String name, String nickName) {
		super(socket, out, in, name);
		this.nick = nickName;
	}

	@Override
	public void run() {
		try {
			String segment = in.readLine();
			try {
				if (segment.startsWith("NICKNAME_END")) {
					
					segment = in.readLine();
					
					if (segment.startsWith("END")) {
						
						nickname.addNickname(name, nick);
						print("HELO");
						print("NAME:" + name);
						print("NICKNAME_START:" + nickname.getNickname(name));
						print("NICKNAME_END");
						print("END");
						out.flush();
					} else {
						wrongProtocol();
					}
				} else {
					wrongProtocol();
				}

			} catch (NameNotFoundException e) {
				print("HELO");
				print("NAME:" + name);
				print("ERR NO_EXIST");
				print("ERR_START:Name unknown:" + name);
				print("ERR_END");
				print("END");
				out.flush();

			} catch (ConflictException e) {
				print("HELO");
				print("NAME:" + name);
				print("ERR DOUBLE");
				print("ERR_START:" + nick + "is already " + name
						+ "'s nickname");
				print("ERR_END");
				out.flush();

				try {
					print("NICKNAME_START:\"" + nickname.getNickname(name)
							+ "\"");
				} catch (NameNotFoundException e1) {
					e1.printStackTrace();
				}
				print("NICKNAME_END");
				print("END");
				out.flush();

			} catch (ExistingNickNameException e) {
				print("HELO");
				print("NAME:" + name);
				print("ERR CONFLIT");
				print("ERR_START:" + nick + "is " + name + "'s nickname");
				print("ERR_END");
				try {
					print("NICKNAME_START:\"" + nickname.getNickname(name));
				} catch (NameNotFoundException e1) {
					e1.printStackTrace();
				}
				print("NICKNAME_END");
				print("END");
				out.flush();
			}

		} catch (IOException e) {
			System.out.println("error-type:IO");
		}
	}

}
