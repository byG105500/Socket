package Appli;

/** Projet Chaussette! 
 * 	Une interface en terminal pour le client **/


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import Connection.Connection;
import Marshaling.Requete;
import Marshaling.RequeteAjout;
import Marshaling.RequeteListe;


public class Client implements Runnable {

	private Scanner sc; // pour lire ce que l'utilisateur note
	private Connection connect;
	
	private enum State {Running, Stop};
	private State currentState = State.Running;
	
		
	public Client() {
		sc = new Scanner(System.in);
	}

	public void connexion() {
		System.out.println("IP du serveur: ");
		String ip = sc.nextLine();

		System.out.println("Port du serveur: ");
		int port = sc.nextInt();
		System.out.println("Connexion à :" + ip + ":" + port + "...");
		try {
			connect = new Connection(ip,port);
			System.out.println("Connexion établie!");

		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ask_action() throws IOException {
		System.out.println("Que voulez vous faire?\n");
		System.out.println("Demander des surnom : D ou Ajouter des surnom: A? D/A/Q? \n");
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

	private void add() throws IOException {
		System.out.println("Quel est le nom auquel ajouter des surnoms?\nNom: ");
		String nom = sc.nextLine();
		System.out.println("Quel sont les surnoms à ajouter à " + nom
				+ "?(séparer les surnoms par des espaces)\nSurnoms: ");
		String surnoms = sc.nextLine();

		Requete requete = new RequeteAjout(nom, surnoms);
		
		connect.envoie_requete(requete);
		String reponse = connect.recevoirReponse();
		
		System.out.println("La réponse: \n" + reponse);
		

	}

	private void ask_list() throws IOException {
		System.out.println("Quel est le nom ?\nNom: ");
		String nom = sc.nextLine();
		
		Requete requete = new RequeteListe(nom);

		connect.envoie_requete(requete);
		String reponse = connect.recevoirReponse();
		System.out.println("La réponse: \n" + reponse);
	}

		@Override
		public void run() {

			connexion();
			try {

			while(currentState == State.Running){
					ask_action();
			}
				connect.quit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void main(String[] args) {
			new Client().run();
		}
}

