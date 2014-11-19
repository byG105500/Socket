package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnexionAccepter implements Runnable {

	private Socket socket;

	private PrintWriter out;
	private BufferedReader in;

	private Thread t;

	public ConnexionAccepter(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("user connected on port : "
					+ socket.getLocalPort());
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			request();
		} catch (IOException e) {
			System.out.println("IO : no clients");
			return;
		}
	}

	private void print(String message) {
		out.println(message);
	}

	private void request() throws IOException {
		String segment;

		if (!(segment = in.readLine()).isEmpty()) {
			if (segment.startsWith("HELO")) {

				segment = in.readLine();
				if (segment.startsWith("NAME")) {

					String data[] = segment.split(":");
					String name = data[1];
					segment = in.readLine();

					if (segment.startsWith("GET")) {

						t = new Thread(new NicknameQuery(socket, out, in, name));
						t.start();
					} else if (segment.startsWith("NICKNAME_START")) {
						String dataAdd[] = segment.split(":");
						String nick = dataAdd[1];
						t = new Thread(new AddNicknameQuery(socket, out, in,
								name, nick));
						t.start();
					} else {
						wrongProtocol();
					}
				} else {
					wrongProtocol();
				}
			} else {
				wrongProtocol();
			}
		}
	}

	private void wrongProtocol() {
		print("HELO");
		print("ERR WRONG_PROTOCOL");
		print("ERR_START:Wrong protocol, request not understood");
		print("ERR_END");
		print("END");
		out.flush();

	}
}
