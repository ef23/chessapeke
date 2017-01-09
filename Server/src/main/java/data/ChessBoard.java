package data;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Set;

import json.*;

/**
 * Represents a chessboard
 */
public class ChessBoard {
	boolean isWhiteTurn;
	Hashtable<Space, ChessPiece> pieces = new Hashtable<Space, ChessPiece>();
	Hashtable<Pieces, MoveHolder[]> moves = new Hashtable<Pieces, MoveHolder[]>();
	
	//"w" is white king is being checked, 
	//"b" is black king is being checked, 
	//"no" is there is no check being done
	String inCheck = "no";
	ChessPiece[] kings;
	
	/**
	 * Queries a space on the chess board
	 * @param query
	 * 			the position to query
	 * @return
	 * 			the piece that is at the position, null if it is empty
	 */
	public ChessPiece getPosition(Space query){
		return pieces.get(query);
	}
	
	/**
	 * Removes a captured piece from the board
	 * @param position
	 * 			the space where the piece has been captured
	 */
	public void capture(Space position){
		pieces.remove(position);
	}
	
	/**
	 * Moves a chess piece
	 * @param from
	 * 			starting space
	 * @param to
	 * 			end space
	 */
	public void move (Space from, Space to){
		ChessPiece moved = pieces.get(from);
		pieces.put(to, moved);
	}
	
	/**
	 * Returns if the king is checked or not
	 * @return
	 * 		the string containing the info
	 */
	public String getIfInCheck(){
		return inCheck;
	}
	
	public enum Pieces {
		WPAWN, WROOK, WKNIGHT, WBISHOP, WQUEEN, WKING,
		BPAWN, BROOK, BKNIGHT, BBISHOP, BQUEEN, BKING;
	}
	
	/**
	 * Goes through pieces and gets valid moves for each
	 * Assumes that validmoves has been updated for each piece prior to receiving valid moves
	 * @return 
	 * 		ArrayList of all possible moves in format (original location)rowcol (endlocation)rowcol
	 * 		* added depending on whether move is capture
	 */
	public ArrayList<String> getMoves()
	{
		ArrayList<String> moves=new ArrayList<String>();
		Set<Space> keys= pieces.keySet();
		for(Space key:keys)
		{
			moves.addAll(pieces.get(key).getValidMoves());//Adds all valid moves to moves array
		}
		return moves;
	}
	
	/**
	 * goes through all pieces and updates the list of valid moves
	 * used each time chess board is updated
	 */
	public void updateValidMoves(){
		Set<Space> keys= pieces.keySet();
		for(Space key:keys)
		{
			pieces.get(key).getMoves();
		}
	}
	//this should be in the client/server
//	public void updateBoard (updateBoard update){
//		for (PieceUpdate change : update.getUpdate()){
//			
//			//if the piece is captured, remove from hashtable
//			if (change.isKilled()){
//				pieces.remove(change.getFrom());
//			} else {
//				//move the piece
//				ChessPiece moved = pieces.get(change.getFrom());
//				pieces.put(change.getTo(), moved);
//			}
//		}
//	}
	
}
