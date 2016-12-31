package data;

import java.util.*;
import json.*;

/**
 * A chess piece on the board
 */
public abstract class ChessPiece {
	
	protected Space position;//Stores position of piece
	
	protected String pieceType;//Stores type of piece
	protected ArrayList<String> validMoves;
	
	protected int[] movement;//Different ways piece can move; row=1s pos, col=10s
	
	protected boolean multiple;//Can this piece move more than one predefined movement
	protected boolean color;//White=true;black=false
	
	protected ChessBoard chessBoard;//Stores reference copy 
	
	/**
	 * Changes position of piece to new position
	 * @param newPosition
	 * @return
	 * 		json encoding of board state
	 */
	public abstract updateBoard move(Space newPosition);
	
	/**
	 * Obtain list of valid moves given current board state and position of piece
	 */
	public abstract void getMoves();//Populates getMoves based on new board state
	
	
	/**
	 * Checks if the coordinate is out of bounds
	 * @param rowCol
	 * 		the rowCol int(row in ones place, column in tens place)
	 * @return
	 * 		true if out of bounds, false if within
	 */
	public static boolean isOutOfBounds(int row, int col)
	{//Checks if row/column combo is out of bounds
		return (row<9&&row>0&&col<9&&col>0)?false:true;//checks to make sure both are within bounds
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 * integer with row in ones pos, col in tens pos or -1 if out of bounds
	 */
	public static int createCoordinates(int row, int col)
	{
		return (!isOutOfBounds(row,col))?row+(col*10):-1;
	}
	
	/**
	 * 
	 * @param rowCol
	 * row in ones place, column in tens place
	 * @return
	 * row
	 */
	public static int getRow(int rowCol)
	{
		return rowCol%10;
	}
	
	/**
	 * 
	 * @param rowCol
	 * row in ones place, column in tens place
	 * @return
	 * column
	 */
	public static int getCol(int rowCol)
	{
		return rowCol/10;
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
	
	/**
	 * 
	 * @param rowCol
	 * row in ones, column in tens
	 * @return
	 * true if space contains the same color,false if it does not
	 */
	public boolean spaceContainsColor(int rowCol)
	{
		return (chessBoard.getPosition(new Space(rowCol)).isColor()==this.color)?true:false;
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
