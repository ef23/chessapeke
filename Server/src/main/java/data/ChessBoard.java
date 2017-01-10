package data;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Set;

import json.*;

/**
 * Represents a chessboard
 */
public class ChessBoard {
	final int CHESSBOARD_WIDTH = 8;
	boolean isWhiteTurn;
	Hashtable<Space, ChessPiece> pieces = new Hashtable<Space, ChessPiece>();
	ArrayList<String> validMoves;
	
	//"w" is white king is being checked, 
	//"b" is black king is being checked, 
	//"no" is there is no check being done
	String inCheck = "no";
	ChessPiece[] kings;
	
	/**
	 * Initializes a new chess board
	 */
	public ChessBoard (){
		//initialize pawns
		boolean isWhite = true;
		
		//should only loop twice anyway
		for (int j = 21; j < 72; j += 50) {
			for (int i = j; i < j + CHESSBOARD_WIDTH; i++) {
				Space pos = new Space(i);
				pieces.put(pos, new Pawn(pos, this, isWhite));
			}
			//second iteration will do black pawns
			isWhite = false;
		}
		
		//initialize the rest of the pieces, black first
		for (int j = 81; j > 10; j -=70){
			int startRow = j;
			int endRow = j - 1 + CHESSBOARD_WIDTH;
			
			Space queenpos = new Space(j + 3);
			pieces.put(queenpos, new Queen(queenpos, this, isWhite));
			
			Space kingpos = new Space (j + 4);
			pieces.put(kingpos, new King(kingpos, this, isWhite));
			
			Space rook = new Space(startRow);
			pieces.put(rook, new Rook(rook, this, isWhite));
			rook = new Space(endRow);
			pieces.put(rook, new Rook(rook, this, isWhite));
			
			Space knight = new Space(++startRow);
			pieces.put(knight, new Knight(knight, this, isWhite));
			knight = new Space(--endRow);
			pieces.put(knight, new Knight(knight, this, isWhite));
			
			Space bishop = new Space(++startRow);
			pieces.put(bishop, new Bishop(bishop, this, isWhite));
			bishop = new Space(--endRow);
			pieces.put(bishop, new Bishop(bishop, this, isWhite));
		}
		getValidMoves();
	} 
	
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
	 * 
	 */
	public void getValidMoves()
	{
		validMoves=new ArrayList<String>();
		Set<Space> keys= pieces.keySet();
		for(Space key:keys)
		{
			validMoves.addAll(pieces.get(key).getValidMoves());//Adds all valid moves to moves array
		}
		//checks for castling opportunities
		
	}
	
	/**
	 * goes through all pieces and updates the list of valid moves within individual piece
	 * used each time chess board is updated
	 */
	public void updateValidMoves(){
		Set<Space> keys= pieces.keySet();
		for(Space key:keys)
		{//get legal moves from within each piece
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
