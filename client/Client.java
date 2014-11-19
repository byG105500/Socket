package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

	private Socket socket;
	private Scanner sc; // pour lire ce que l'utilisateur note

	private enum State {Running, Stop};
	private State currentState = State.Running;
	
	private Thread t;
	
	public Client() {
		sc = new Scanner(System.in);
	}

	public void connexion() {
		System.out.println("IP du serveur: ");
		String ip = "10.212.115.185"; //sc.nextLine();

		System.out.println("Port du serveur: ");
		int port = 2048; //sc.nextInt();
		System.out.println("Connexion à :" + ip + ":" + port + "...");
		try {
			socket = new Socket(ip, port);
			System.out.println("Connexion établie avec " + ip + ":" + port);

		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ask_action() {
		System.out.println("Que voulez vous faire?\n");
		System.out
				.println("Demander des surnom : D ou Ajouter des surnom: A? D/A? \n");
		sc = new Scanner(System.in);
		String rep = sc.nextLine();
		switch (rep) {
		case "A":
			add();
			break;
		case "D":
			ask_list();
			break;
		case "Q":
			currentState = State.Stop;
			break;
		}
	}

	private void add() {
		System.out.println("Quel est le nom auquel ajouter des surnoms?\nNom: ");
		String name = sc.nextLine();
		System.out.println("Quel sont les surnoms à ajouter à " + name
				+ "?(séparer les surnoms par des espaces)\nSurnoms: ");
		String nicknames = sc.nextLine();

		t = new Thread(new AddQuery(socket, name, nicknames));
		t.start();

	}

	private void ask_list() {
		System.out.println("Quel est le nom ?\nNom: ");
		String name = sc.nextLine();

		t = new Thread(new Query(socket, name));
		t.start();

	}

	@Override
	public void run() {

		connexion();
		while(currentState == State.Running){
			if(t==null || !t.isInterrupted()){
				ask_action();
			}
		}
		sc.close();

	}

	public static void main(String[] args) {

		new Client().run();

	}
}
