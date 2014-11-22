package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Marshaling.Requete;
import Marshaling.RequeteNonConstruiteException;




public class Connection{
	
	private PrintWriter out;
	private BufferedReader in;
	private Socket chaussette;
	
	public Connection(String ip, int port) throws UnknownHostException, IOException{
		chaussette = new Socket(ip, port);
		out = new PrintWriter(chaussette.getOutputStream());
		in = new BufferedReader(new InputStreamReader(chaussette.getInputStream()));
	}
	
	public void envoie_requete(Requete requete){
		try{
			String r = requete.construireRequete();
			
			System.out.println("Ma reqete:\n" + r + "\n\n");
			out.println(r); // pour ajouter à la socket
			out.flush(); // pour envoyer la socket
			System.out.println("Requete envoyée ! \n\n La réponse: \n");
		}catch(RequeteNonConstruiteException e){e.printStackTrace();}
	}
	
	public String recevoirReponse() throws IOException{
		String reponse="";
		String ligne;
		while (!(ligne = in.readLine()).isEmpty()) {
			reponse+=ligne+"\n";
		}
		return reponse;
	}

	public void quit() throws IOException{
		chaussette.close();
	}
	
	
}
