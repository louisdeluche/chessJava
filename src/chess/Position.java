package chess;

public class Position {
	
	int posX;
	int posY;
	
	Position(int x, int y)
	{
		//x = colonne
		//Y = ligne
		this.posX = x;
		this.posY = y;
	}

	@Override
	public String toString() {
		return "Position [posX=" + posX + ", posY=" + posY + "]";
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public boolean equals(Position posArrive)
	{
		return this.posX == posArrive.posX && this.posY == posArrive.posY;
	}

}
