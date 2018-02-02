package chess;

public class Fou extends Piece {
	Fou(String color)
	{
		super("Fou", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement d) {
		
		return d.deplacementX - d.deplacementY == 0 && !d.depart.equals(d.arrive);
	}
}
