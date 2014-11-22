package Marshaling;

/** Classe mère de AddQwery et ListQwery qui font les requettes string.
 * Cette classe n'est jamais instanciée.
 * @author Mayl
 */
public abstract class Requete{
	
	protected String nom; /**Une requete fait toujours référence à un nom **/
	
	public Requete(String nom){
		this.nom=nom;
	}
	
	public String construireRequete() throws RequeteNonConstruiteException{
		throw new RequeteNonConstruiteException();
	}
	
	

}
