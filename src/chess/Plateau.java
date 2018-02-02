package chess;

public class Plateau {
	
	private Case[][] cases = new Case[8][8];
	
	public Plateau () {
		for(int x = 0; x < 8; x++)
		{
			for(int y = 0; y < 8; y++)
			{
				cases[x][y] = new Case();
			}
		}
	}
	
	public boolean cheminPossible(Piece piece, Deplacement d)
	{			
		if(piece.getNom() != "Cavalier" || piece.getNom() == "Roi")
		{
			return true;
		}
		else if (piece.getNom() == "Pion")
		{					
			return !cases[d.arrive.posX][d.arrive.posY].containPiece();
		}
		
		if(piece.getNom() == "Fou" || piece.getNom() == "Reine")
		{
			int departY = d.depart.posY;
			int departX = d.depart.posX;
			
			if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y< d.arrive.posY; y++, x--)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}
			else if(d.getDirectionX() == "droite" && d.getDirectionY() == "bas")
			{
				for(int y = departY,x = departX; y< d.arrive.posY; y++, x++)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}
			else if(d.getDirectionX() == "gauche" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y> d.arrive.posY; y--, x--)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}		
			else if(d.getDirectionX() == "droite" && d.getDirectionY() == "haut")
			{
				for(int y = departY,x = departX; y> d.arrive.posY; y--, x++)
				{
					if(cases[x][y].containPiece()) return false;
				}
			}	
			
			return true;
			
		}
		if(piece.getNom() == "Tour" || piece.getNom() == "Reine")
		{
			if(d.axeX)
			{
				int depart = d.depart.posY;
				int arrive = d.arrive.posY;
				if(d.getDirectionX() == "gauche")
				{
					depart = d.arrive.posY;
					arrive = d.depart.posY;
				}
				
				for(int i = depart; i < arrive; i++)
				{
					if(cases[d.depart.posX][i].containPiece()) return false;
					
				}	
				return true;
			}
			else if(d.axeY)
			{
				int depart = d.depart.posX;
				int arrive = d.arrive.posX;
				if(d.getDirectionX() == "haut")
				{
					depart = d.arrive.posX;
					arrive = d.depart.posX;
				}
				
				for(int i = depart; i < arrive; i++)
				{
					if(cases[i][d.depart.posY].containPiece())
					{
						return false;
					}
					
				}
				
				return true;
			}
		}

		return false;	
	}
	
	public Deplacement selectionCase()
	{
		//Recuperer les valeurs des positions de depart et d'arrivé
		return new Deplacement(new Position(), new Position());
	}
	
	public void deplacerPiece(Piece piece, Deplacement d)
	{
		Case cible = cases[d.arrive.posX][d.arrive.posY];
		
		if(cible.containPiece() && cible.getPiece().getCouleur() != piece.getCouleur() && cheminPossible(piece, d) && piece.deplacementPossible(d))
		{
			if(cible.getPiece().getNom() == "Roi") finPartie();			
			cible.setPiece(piece);			
		}		
	}

	public void nouvellePartie()
	{
		String[] ordre = {"Tour", "Cavalier", "Fou", "Reine", "Roi", "Fou", "Cavalier", "Tour"};
		
		for(int i = 0; i < ordre.length; i++)
		{
			cases[1][i] = new Case(new Pion("noir"));
			cases[6][i] = new Case(new Pion("blanc"));
			
			switch (ordre[i])
			{
				case "Tour":
					cases[0][i] = new Case(new Tour("noir") );
					cases[7][i] = new Case(new Tour("blanc"));	
				break;
				case "Cavalier":
					cases[0][i] = new Case(new Cavalier("noir"));
					cases[7][i] = new Case(new Cavalier("blanc"));	
				break;	
				case "Fou":
					cases[0][i] = new Case(new Fou("noir"));
					cases[7][i] = new Case(new Fou("blanc"));	
				break;	
				case "Reine":
					cases[0][i] = new Case(new Reine("noir"));
					cases[7][i] = new Case(new Reine("blanc"));	
				break;	
				case "Roi":
					cases[0][i] = new Case(new Roi("noir"));
					cases[7][i] = new Case(new Roi("blanc"));	
				break;					
			}
		}
		
	}
	
	public void finPartie()
	{
		//disable event
		System.out.println("Fin de partie");
	}

}
