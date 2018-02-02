package chess;

public class Cavalier extends Piece {
	Cavalier(String color)
	{
		super("Cavalier", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement d) {		
		//x / 2 == 2 si on va de deux cases vers la gauche/droite ou est egal a 0.5 si on va de deux cases en bas ou en haut
		return d.deplacementX / d.deplacementY == 2 || d.deplacementX / d.deplacementY == 0.5;
	}
}
