package chess;

public abstract class Piece {
	
	private String nom;
	private String couleur;
	
	Piece(String nom, String couleur)
	{
		this.nom = nom;
		this.couleur = couleur;
	}
	
	protected abstract boolean deplacementPossible();

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
}
