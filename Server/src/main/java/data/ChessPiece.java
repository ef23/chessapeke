package data;

/**
 * A chess piece on the board
 */
public abstract class ChessPiece {
	protected Space position;
	
	public abstract void move(Space newPosition);
	
}
