package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Query implements Runnable {

	protected PrintWriter out;
	protected BufferedReader in;
	protected Socket socket;
	
	protected NickNameDataBase nickname;
	protected String name;

	protected Query(Socket socket, PrintWriter out, BufferedReader in, String name) {
		this.socket = socket;
		this.out = out;
		this.in = in;
		this.name = name;
		this.nickname = NickNameDataBase.getInstance();
	}
	
	@Override
	public abstract void run();
	
	protected void print(String message) {
		out.println(message);
	}

	protected void wrongProtocol() {
		print("HELO");
		print("ERR WRONG_PROTOCOL");
		print("ERR_START:Wrong protocol, request not understood");
		print("ERR_END");
		print("END");
		out.flush();
	}
}
