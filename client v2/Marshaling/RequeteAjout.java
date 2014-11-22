package Marshaling;

public class RequeteAjout extends Requete {

	private String surnoms;

	public RequeteAjout(String nom, String surnoms) {
		super(nom);
		this.surnoms = surnoms;
	}

	public String construireRequete(){
		String requete = "";
		requete += "HELO\n";
		requete += "NAME:" + nom + "\n";
		requete += "NICKNAME_START:" + surnoms + "\n";
		requete += "NICKNAME_END\n";
		requete += "END\n";
		return requete;		
	}

}



