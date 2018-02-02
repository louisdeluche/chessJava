package chess;

public class Plateau {
	
	private Case[][] cases = new Case[8][8];
	
	//On instancie notre plateau en le 
	public Plateau () {
		for(int x = 0; x < 8; x++)
		{
			for(int y = 0; y < 8; y++)
			{
				cases[x][y] = new Case();
			}
		}
	}

}
