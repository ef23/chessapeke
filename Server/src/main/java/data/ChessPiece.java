package data;

import java.util.*;
import json.*;

/**
 * A chess piece on the board
 */
public abstract class ChessPiece {
	protected Space position;//Stores position of piece
	
	protected String pieceType;//Stores type of piece
	
	protected int[] movement;//Different ways piece can move; row=1s pos, col=10s
	
	protected boolean multiple;//Can this piece move more than one predefined movement
	protected boolean color;//White=true;black=false
	
	protected ChessBoard chessBoard;//Stores reference copy 
	
	public abstract updateBoard move(Space newPosition);
	public abstract ArrayList<Space> getMoves();
	
	
	/**
	 * Checks if the coordinate is out of bounds
	 * @param rowCol
	 * 		the rowCol int
	 * @return
	 * 		true if out of bounds, false if within
	 */
	public static boolean isOutOfBounds(int rowCol)
	{//Checks if row/column combo is out of bounds
		return (rowCol%10<=8&&rowCol/10<=8&&rowCol%10>=1&&rowCol/10>=1)?false:true;//checks to make sure both are within bounds
	}

	/**
	 * @return the position
	 */
	public Space getPosition() {
		return position;
	}
	/**
	 * @return the pieceType
	 */
	public String getPieceType() {
		return pieceType;
	}
	/**
	 * @return the movement
	 */
	public int[] getMovement() {
		return movement;
	}
	/**
	 * @return the multiple
	 */
	public boolean isMultiple() {
		return multiple;
	}
	/**
	 * @return the color
	 */
	public boolean isColor() {
		return color;
	}
	/**
	 * @return the chessBoard
	 */
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof ChessPiece){
			ChessPiece cp = (ChessPiece) o;
			return position.equals(cp.getPosition()) && pieceType.equals(cp.getPieceType())
					&& color == cp.isColor() && chessBoard.equals(cp.getChessBoard());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		//make it based off position, piece type, and color
		return position.hashCode() + (pieceType + color + "").hashCode();
	}
}
