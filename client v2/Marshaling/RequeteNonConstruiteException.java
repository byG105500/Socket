package Marshaling;

public class RequeteNonConstruiteException extends Exception{

	public RequeteNonConstruiteException(){
	    System.out.println("La requette n'as pas �t� construite. Cela "
	    		+ "peut �tre du � un mauvais appel de la fonction 'construireRequete' appel�e par la classe Connection.");
	  }  
}

