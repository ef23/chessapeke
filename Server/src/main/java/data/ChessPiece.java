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
	
	
	
	public static boolean isOutOfBounds(int rowCol)
	{//Checks if row/column combo is out of bounds
		return (rowCol%10<=8&&rowCol/10<=8&&rowCol%10>=1&&rowCol/10>=1)?false:true;//checks to make sure both are within bounds
	}
}
