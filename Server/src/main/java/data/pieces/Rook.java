package data.pieces;

import data.ChessBoard;
import data.ChessPieceVisitor;
import data.PieceVisitor;
import data.Space;

public class Rook extends ChessPiece implements PieceVisitor{

	public Rook(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="rook";
		position=positionIn;
		color=colorIn;
		isMoved=false;
		movement=new int[]{10,01};//Movement pattern for rook
		this.chessBoard=chessBoardIn;//Reference copy of chess board
		getMoves();
	}
	
	@Override
	public void getMoves() {
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
