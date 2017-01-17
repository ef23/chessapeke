package data.pieces;

import data.ChessBoard;
import data.ChessPieceVisitor;
import data.PieceVisitor;
import data.Space;

public class Bishop extends ChessPiece implements PieceVisitor{
	public Bishop(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="bishop";
		position=positionIn;
		color=colorIn;
		isMoved=false;
		movement=new int[]{11};//Movement pattern for bishop
		this.chessBoard=chessBoardIn;//Reference copy of chess board
	}
	

	@Override
	public void getMoves(){
		for(int change:movement)
		{
			startRecursiveGetMoves(change);
		}
	}
	
	@Override
	public String accept(ChessPieceVisitor v){
		return v.visit(this);
	}
}
