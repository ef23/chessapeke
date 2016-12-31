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
		return (chessBoard.getPosition(new Space(rowCol)).isColor()==this.color);
	}
	
	/**
	 * 
	 * @param rowCol
	 * row in ones, column in tens
	 * @return
	 * true if space contains opposite color, false if space is empty or contains same color
	 */
	public boolean spaceContainsOppositeColor(int rowCol)
	{
		return (chessBoard.getPosition(new Space(rowCol))!=null&&chessBoard.getPosition(new Space(rowCol)).isColor()!=this.color);
	}
	
	/**
	 * 
	 * @param change
	 * base movement for piece
	 */
	protected void startRecursiveGetMoves(int change)
	{
		int changeRow=ChessPiece.getRow(change);
		int changeCol=ChessPiece.getCol(changeRow);
		int rowAdd=ChessPiece.getRow(position.getSpace())+ChessPiece.getRow(change);
		int colAdd=ChessPiece.getCol(position.getSpace())+ChessPiece.getCol(change);
		int rowMinus=ChessPiece.getRow(position.getSpace())-ChessPiece.getRow(change);
		int colMinus=ChessPiece.getCol(position.getSpace())-ChessPiece.getCol(change);
		this.recursiveGetMoves(rowAdd, colAdd, changeRow, changeCol);
		this.recursiveGetMoves(rowAdd, colMinus, changeRow, changeCol);
		this.recursiveGetMoves(rowMinus, colAdd, changeRow, changeCol);
		this.recursiveGetMoves(rowMinus, colMinus, changeRow, changeCol);
	}
	/**
	 * 
	 * @param row
	 * row index we check
	 * @param col
	 * column index we check
	 * @param changeRow
	 * amount we add to row to get next row val to check
	 * @param changeCol
	 * amount we add to column to get next col val to check
	 */
	protected void recursiveGetMoves(int row, int col, int changeRow, int changeCol)
	{
		//Base cases
		if(ChessPiece.isOutOfBounds(row,col)||this.spaceContainsColor(createCoordinates(row,col)))
			return;//invalid index, finished
		if(this.spaceContainsOppositeColor(createCoordinates(row,col)))
		{
			validMoves.add(position.getSpace()+" "+createCoordinates(row,col));
			return;//valid index, but final valid index
		}
		//otherwise, space is clear and it is legal to move here
		validMoves.add(position.getSpace()+" "+createCoordinates(row,col));
		recursiveGetMoves(row+changeRow,col+changeCol,changeRow,changeCol);
		return;
	}
	
	/**
	 * 
	 * @param change
	 * rowCol combination for a single unit of movement for this piece
	 */
	protected void nonRecursiveGetMoves(int change)
	{
		int rowAdd=ChessPiece.getRow(position.getSpace())+(ChessPiece.getRow(change));//Adding to position; row
		int rowMinus=ChessPiece.getRow(position.getSpace())-(ChessPiece.getRow(change));//Subtracting from position; row
		int colAdd=ChessPiece.getCol(position.getSpace())+(ChessPiece.getCol(change));//Adding to position; column
		int colMinus=ChessPiece.getCol(position.getSpace())-(ChessPiece.getCol(change));//Subtracting from position; column
		int[] positions=new int[]{createCoordinates(rowAdd,colAdd),createCoordinates(rowAdd,colMinus),
				createCoordinates(rowMinus,colAdd),createCoordinates(rowMinus,colMinus)};
		for(int rowCol:positions)
		{
			if(rowCol!=-1&&!this.spaceContainsColor(rowCol))
			{//If not out of bounds and space does not contain the same color, add to list of valid moves
				validMoves.add(position.getSpace()+" "+rowCol);
			}
		}
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
