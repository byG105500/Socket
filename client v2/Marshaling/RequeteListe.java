package Marshaling;

public class RequeteListe extends Requete{

	public RequeteListe(String nom) {
		super(nom);
	}
	
	public String construireRequete(){
		String requete = "";
		requete+="HELO\n";
		requete+="NAME:"+nom+"\n";
		requete+="GET\n";
		requete+="END\n";
		return requete;
	}
}
