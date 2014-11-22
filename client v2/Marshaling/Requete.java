package Marshaling;

/** Classe m�re de AddQwery et ListQwery qui font les requettes string.
 * Cette classe n'est jamais instanci�e.
 * @author Mayl
 */
public abstract class Requete{
	
	protected String nom; /**Une requete fait toujours r�f�rence � un nom **/
	
	public Requete(String nom){
		this.nom=nom;
	}
	
	public String construireRequete() throws RequeteNonConstruiteException{
		throw new RequeteNonConstruiteException();
	}
	
	

}
