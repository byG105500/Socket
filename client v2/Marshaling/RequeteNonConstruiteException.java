package Marshaling;

public class RequeteNonConstruiteException extends Exception{

	public RequeteNonConstruiteException(){
	    System.out.println("La requette n'as pas été construite. Cela "
	    		+ "peut être du à un mauvais appel de la fonction 'construireRequete' appelée par la classe Connection.");
	  }  
}

