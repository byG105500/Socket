package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import server.exception.NameNotFoundException;

public class NicknameQuery extends Query implements Runnable {

	public NicknameQuery(Socket socket, PrintWriter out, BufferedReader in,
			String name) {
		super(socket, out, in, name);
	}

	@Override
	public void run() {
		String segment;
		try {
			segment = in.readLine();

			try {
				String response = nickname.getNickname(name).toString();
				if (segment.startsWith("END")) {
					print("HELO");
					print("NAME:" + name);
					print("NICKNAME_START:" + response);
					print("NICKNAME_END");
					print("END");
					out.flush();
				} else {
					wrongProtocol();
				}
			} catch (NameNotFoundException e) {
				print("HELO");
				print("ERR NO_EXIST");
				print("ERR_START:Name unknown:" + name);
				print("ERR_END");
				print("END");
				out.flush();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
