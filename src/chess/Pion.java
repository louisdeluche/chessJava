package chess;

public class Pion extends Piece {
	
	Pion(String color)
	{
		super("Pion", color);
	}

	@Override
	protected boolean deplacementPossible(Deplacement d) {
		
		boolean firstMouv = (d.depart.posY == 6 && this.couleur ==  "blanc") || (d.depart.posY == 1 && this.couleur ==  "noir");
		
		int diffMouv = d.arrive.posY - d.depart.posY;
		
		String direction = diffMouv > 0 ? "descendre" : "monter";
		
		boolean possible;
		System.out.println(d.depart.posX);
		System.out.println(d.depart.posY);
		System.out.println(d.deplacementY);
		if(firstMouv)
		{
			possible = d.deplacementY <= 2 && d.deplacementX == 0;
		}
		else
		{
			possible = d.deplacementY == 1 && d.deplacementX == 0;
		}	
		
		System.out.println(possible);
		System.out.println(direction);
		
		return possible && ((this.couleur == "noir" && direction == "descendre") || (this.couleur == "blanc" && direction == "monter"));
	}
}
