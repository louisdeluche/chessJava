package chess;

public class Tour extends Piece {
	Tour(String color)
	{
		super("Tour", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement deplacement) {
		return deplacement.arrive.posX == deplacement.depart.posX || deplacement.arrive.posY == deplacement.depart.posY;	
	}
}
