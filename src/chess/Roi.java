package chess;

public class Roi extends Piece {
	Roi(String color)
	{
		super("Roi", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement d) {
		
		return (d.deplacementX == 1 && d.deplacementY == 1) || (d.deplacementX == 1 && d.deplacementY == 0) || (d.deplacementX == 0 && d.deplacementY == 1);
	}
}
