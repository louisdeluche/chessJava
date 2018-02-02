package chess;

public class Case {

	public Piece piece = null;
	
	
	Case(Piece piece)
	{
		this.piece = piece;
	}

	Case()
	{
		
	}
	
	public boolean containPiece() {
		return this.piece != null;
	}
	
	public void removePiece() {
		this.piece = null;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}	
}
