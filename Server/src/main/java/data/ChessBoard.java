package data;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Set;

import data.pieces.*;
import data.MoveInterpreter;
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
	
	LinkedList<PieceUpdate> change = new LinkedList<PieceUpdate>();
	
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
			
			isWhite=true;
		}
		updateValidMoves();
		getValidMoves();
	} 
	
	/**
	 * copy constructor
	 * @param chessBoardIn chessboard we are copying from
	 */
	public ChessBoard(ChessBoard chessBoardIn)
	{
		pieces=new Hashtable<Space, ChessPiece>(chessBoardIn.pieces);
		validMoves=new ArrayList<String>(chessBoardIn.validMoves);
		isWhiteTurn=chessBoardIn.isWhiteTurn;
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
	public void move (Space from, Space to,Space passantLoc){
		if(this.inCheck.equals("no") || this.stillInCheck(from, to,passantLoc)==false)
		{
			String player = (isWhiteTurn)?"white":"black";
			ChessPiece captured = null;
			
			//if a piece was captured, add it to the list of changes
			if ((captured = executeMove(from, to,passantLoc))!=null){
				change.add(new PieceUpdate(player, captured.accept(new ChessPieceVisitor()), from, to, true));
			}
			
			change.add(new PieceUpdate(player, pieces.get(to).accept(new ChessPieceVisitor()), from, to, false));
			this.updatePieces();
		}
	}
	
	/**
	 * directly executes move
	 * @param from 
	 * 		starting space
	 * @param to 
	 * 		ending space
	 * @return
	 * 		the piece, if any, that was captured. null if none
	 */
	protected ChessPiece executeMove(Space from, Space to,Space passantLoc)
		{
		ChessPiece captured = null;
		ChessPiece moved = pieces.get(from);
		
		//if move is en passant
		if(passantLoc!=null)
		{
			captured=pieces.get(passantLoc);
			capture(passantLoc);
		}
		//if it is moving to a piece that is currently occupied
		else if ((captured = pieces.get(to)) != null){
			capture(to);
		}
		pieces.remove(from);
		pieces.put(to, moved);
		isWhiteTurn=(isWhiteTurn)?false:true;//update whose turn it is
		updateValidMoves();
		getValidMoves();
		changeCheckState();
		
		return captured;
	}
	/**
	 * Returns if the king is checked or not
	 * @return
	 * 		the string containing the info
	 */
	public String getIfInCheck(){
		return inCheck;
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
		if(canKingSideCastle())
			validMoves.add(isWhiteTurn+" KC");
		if(canQueenSideCastle())
		{
			validMoves.add(isWhiteTurn+" QC");
		}
	}
	
	/**
	 * goes through all pieces and updates the list of valid moves within individual piece
	 * used each time chess board is updated
	 */
	public void updateValidMoves(){
		Set<Space> keys= pieces.keySet();
		ChessPieceVisitor v = new ChessPieceVisitor();
		for(Space key:keys)
		{//get legal moves from within each piece
			//System.out.println(key.getSpace() + " " + pieces.get(key).accept(v));
			pieces.get(key).getMoves();
		}
	}
	
	/**
	 * 
	 * @return ability to kingside castle
	 */
	private boolean canKingSideCastle()
	{
		Space kingPos=(isWhiteTurn)?new Space(51):new Space(58);
		Space rookPos=(isWhiteTurn)?new Space(81):new Space(88);
		Space bishopPos=(isWhiteTurn)?new Space(61):new Space(68);
		Space knightPos=(isWhiteTurn)?new Space(71):new Space(78);
		
		//if either of these positions are null, then it has moved
		if (pieces.get(kingPos) == null || pieces.get(rookPos) == null)
			return false;	
		return(pieces.get(kingPos).getPieceType().equals("king")&&
				pieces.get(kingPos).hasMoved()==false&&
				pieces.get(rookPos).getPieceType().equals("rook")&&
				pieces.get(rookPos).hasMoved()==false&&
				pieces.get(knightPos)==null&&
				pieces.get(bishopPos)==null)?true:false;
	}
	/**
	 * 
	 * @return ability to queenside castle
	 */
	private boolean canQueenSideCastle()
	{
		Space kingPos=(isWhiteTurn)?new Space(51):new Space(58);
		Space queenPos=(isWhiteTurn)?new Space(41):new Space(48);
		Space rookPos=(isWhiteTurn)?new Space(11):new Space(18);
		Space bishopPos=(isWhiteTurn)?new Space(31):new Space(38);
		Space knightPos=(isWhiteTurn)?new Space(21):new Space(28);
		
		//if any of these positions are null, then it has moved
		if (pieces.get(kingPos) == null 
				|| pieces.get(rookPos) == null 
				|| pieces.get(queenPos) == null)
			return false;

		return(pieces.get(kingPos).getPieceType().equals("king")&&
				pieces.get(kingPos).hasMoved()==false&&
				pieces.get(queenPos).getPieceType().equals("queen")&&
				pieces.get(queenPos).hasMoved()==false&&
				pieces.get(rookPos).getPieceType().equals("rook")&&
				pieces.get(rookPos).hasMoved()==false&&
				pieces.get(knightPos)==null&&
				pieces.get(bishopPos)==null)?true:false;
	}
	
	//will probably be changed
	/**
	 * Parses a move string with the given piece type and destination
	 * @param pieceType
	 *			the type of piece given
	 * @param destination
	 * 			the destination
	 */
	public void parseMove(String pieceType, Space destination){
		boolean isOnlyPiece = true;
		String startLoc = "";
		int passantLoc=-1;
		for (String move : validMoves){
			//if this move is the target type of piece
			if (move.indexOf(pieceType) != -1){
				//System.out.println(move + " " + " " + destination.getSpace() + move.indexOf(destination.getSpace() + ""));
				if (move.indexOf(destination.getSpace() + "") != -1){
					//make sure that only one piece of given type can move to the location
					if (isOnlyPiece){
						isOnlyPiece = false;
					} else {
						//TODO throw an error
					}
					String[] parts = move.split(" ");
					startLoc = parts[2];
					
					//If there is a location after *, this move represents en passant, store location for capture
					if (move.indexOf("*") != -1) {
						passantLoc = Integer.parseInt(parts[parts.length - 2]);
					}
				}
			}
		}
		if (startLoc.equals("")){
			System.out.println("invalid move u fucking idiot");
			//TODO throw error
		}
		System.out.println(startLoc);
		move(new Space(Integer.parseInt(startLoc)), destination,(passantLoc==-1)?null:new Space(passantLoc));
	}
	
	/**
	 * Checks to see if given color still in check after move has been executed
	 * @param from location that we want to move from
	 * @param to location that we want to move to
	 * @return 
	 *   true if still in check once move is executed
	 * 	false if move removes us from check after being executed
	 */
	public boolean stillInCheck(Space from, Space to, Space passantLoc)
	{
		ChessBoard sandbox=new ChessBoard(this);//creates sandbox chessboard to test moves out on
		int kingSpace=this.findPieceLocation("king", sandbox.isWhiteTurn);//stores location of king that is currently in check(column in tens, row in ones)
		sandbox.executeMove(from, to, passantLoc);//executes move on sandbox
		for(String validMove:sandbox.validMoves)
		{
			String color=MoveInterpreter.findColor(validMove);//Color of piece that is moving
			int endLocation=MoveInterpreter.findEndLocation(validMove);
			//if location piece is attacking is king's location, and piece is of opposite color to king (sandbox.isWhiteTurn now represents opposite color since move has been executed)
			if(color.equals((sandbox.isWhiteTurn)?"white":"black")&&
					endLocation==kingSpace)
			{
				return true;
			}
		}
		return false;//If we never came upon piece attacking king's location, safe to assume this color is no longer in check after move
	}
	
	/**
	 * checks whether current color is in check after last move was executed
	 */
	public void changeCheckState()
	{
		boolean oppositeColor=(this.isWhiteTurn)?false:true;
		int kingSpace=this.findPieceLocation("king",this.isWhiteTurn);//Location of king that we want to see if is now in check
		//search through validMoves to see if king appears as target
		for(String validMove:this.validMoves)
		{
			String color=validMove.split(" ")[1];
			int endLocation= Integer.parseInt(validMove.split(" ")[3]);
			//if endlocation of move matches the king of the color we are checking, and attacking piece color is the opposite of king's
			if(color.equals((this.isWhiteTurn)?"black":"white")&&
					endLocation==kingSpace)
			{
				//change check state accordingly, current color is now in check
				this.inCheck=(this.isWhiteTurn)?"w":"b";
				return;
			}
		}
		//if we didn't find that the current color is in check, then nobody is in check (other color could not execute move while in check)
		this.inCheck="no";
	}
	
	/**
	 * Parses a JSON containing the updates to the board
	 * @param update
	 * 		the json object 
	 */
	public void updateBoard (UpdateBoard update){
		
		for (PieceUpdate change : update.getUpdate()){
			//if the piece is captured, remove from hashtable
			if (change.isKilled()){
				pieces.remove(change.getFrom());
			} else {
				executeMove(change.getFrom(), change.getTo(),null);
			}
		}
		
		//set up for next turn
		if (isWhiteTurn)
			isWhiteTurn = false;
		change = new LinkedList<PieceUpdate>();
	}
	
	/**
	 * 
	 * @param type
	 * 		type of chess piece ie king, rook, etc
	 * @param color
	 * 		color of piece
	 * @return
	 * 		location of first piece to fit parameters with column in tens position, row in ones position, or -1 if could not find
	 */
	public int findPieceLocation(String type, boolean color)
	{
		//iterate through hashtable via locations
		for(Entry<Space, ChessPiece> entry:this.pieces.entrySet())
		{
			//if piece type is same as given, and color is same as given
			if(entry.getValue().getPieceType().equals(type)&&
					entry.getValue().isColor()==color)
				return entry.getKey().getSpace();
		}
		return -1;
	}
	/**
	 * temp ascii representation
	 */
	public void displayBoard(){
		char[][] board = new char[10][10];
		char start = 'a';
		for (int i = 1; i < board.length-1; i++){
			board[i][0] =  (char) ('9' - (char) i);
		}
		
		for (int i = 1; i < board[9].length-1; i++){
			board[9][i] = start;
			start++;
		}
		
		for (int i = 1; i < board.length-1; i++){
			for (int j = 1; j < board[0].length-1; j++){
				board[i][j] = '-';
			}
		}
		
		for (Space space : pieces.keySet()){
			int col = space.getSpace()/10;
			int row = space.getSpace()%10;
			ChessPieceVisitor v = new ChessPieceVisitor();
			switch(pieces.get(space).accept(v)){
			case "pawn":
				board[col][row] = 'P';
				break;
			case "rook":
				board[col][row] = 'R';
				break;
			case "knight":
				board[col][row] = 'N';
				break;
			case "bishop":
				board[col][row] = 'B';
				break;
			case "king":
				board[col][row] = 'K';
				break;
			case "queen":
				board[col][row] = 'Q';
				break;
			}
		}
		
		for (int i = 1; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * goes through pieces and updates chess board to this one
	 */
	public void updatePieces()
	{
		for(Space key:pieces.keySet())
		{
			this.getPosition(key).setBoard(this);
		}
	}
}
