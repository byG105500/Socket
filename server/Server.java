package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private int port;

	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("connected on port : "
					+ serverSocket.getLocalPort());

			while (true) {

				Socket socket = serverSocket.accept();
				Thread t = new Thread(new ConnexionAccepter(socket));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server(2048).run();
	}
}