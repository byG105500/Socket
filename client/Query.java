package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Query extends Thread {

	
	private PrintWriter out;
	private BufferedReader in;
	private Socket s;
	String name;

	
	public Query(Socket s, String name) {
		this.s = s;
		this.name = name;
	}
	
	private void send_request(PrintWriter out){
		String request = "";
		request+="HELO\n";
		request+="NAME:"+name+"\n";
		request+="GET\n";
		request+="END\n";
		
		System.out.println("Ma reqete:\n" + request + "\n\n");

		out.println(request); //pour ajouter à la socket
		out.flush(); //pour envoyer la socket
		System.out.println("Requete envoyée ! \n\n La réponse: \n");

	}
	
	@Override
	public synchronized void start() {
		run();
	}

	@Override
	public void run() {

		try {
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			send_request(out);

			String output;
			
			while (!(output = in.readLine()).isEmpty()) {
				System.out.println(output);
			}
			interrupt();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    

}
