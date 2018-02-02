package chess;

public class Pion extends Piece {
	
	Pion(String color)
	{
		super("Pion", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement d) {
		
		boolean firstMouv = d.depart.equals(d.arrive);
		
		int diffMouv = d.arrive.posX - d.depart.posX;
		
		String direction = diffMouv > 0 ? "descendre" : "monter";
		
		boolean possible;
		if(firstMouv)
		{
			possible = d.deplacementX <= 2;
		}
		else
		{
			possible = d.deplacementX == 1;
		}	
		
		return possible && ((this.couleur == "noir" && direction == "descendre") || (this.couleur == "blanc" && direction == "monter"));
	}
}
