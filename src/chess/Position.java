package chess;

public class Position {
	
	int posX;
	int posY;
	
	Position(int x, int y)
	{
		this.posX = x;
		this.posY = y;
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
