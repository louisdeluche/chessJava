package chess;

public class Reine extends Piece {
	Reine(String color)
	{
		super("Reine", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement d) {		
		return (d.arrive.posX == d.depart.posX || d.arrive.posY == d.depart.posY) || (d.deplacementX - d.deplacementY == 0 && !d.depart.equals(d.arrive));	
	}
}
