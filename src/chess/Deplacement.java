package chess;

public class Deplacement {
	
	public Position arrive;
	public Position depart;
	public double deplacementX;
	public double deplacementY;
	public boolean axeX = false;
	public boolean axeY = false;
	
	public Deplacement(Position arrive, Position depart)
	{
		this.arrive = arrive;
		this.depart = depart;
		
		this.deplacementX = Math.abs(arrive.posX - depart.posX);	
		this.deplacementY = Math.abs(arrive.posY - depart.posY);		
		
		if(depart.posX == arrive.posX) this.axeX = true;
		if(depart.posY == arrive.posY) this.axeY = true;
	}
	
	@Override
	public String toString() {
		return "Deplacement [arrive=" + arrive + ", depart=" + depart + ", deplacementX=" + deplacementX
				+ ", deplacementY=" + deplacementY + ", axeX=" + axeX + ", axeY=" + axeY + "]";
	}

	public String getDirectionX()
	{
		return depart.posX - arrive.posX < 0 ? "droite" : "gauche";
	}

	public String getDirectionY()
	{
		return depart.posY- arrive.posY < 0 ? "bas" : "haut";
	}

}
