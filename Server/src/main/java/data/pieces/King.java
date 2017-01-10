package data.pieces;

import data.ChessBoard;
import data.ChessPieceVisitor;
import data.PieceVisitor;
import data.Space;

public class King extends ChessPiece implements PieceVisitor{
	
	public King(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="king";
		position=positionIn;
		isMoved=false;
		color=colorIn;
		movement=new int[]{10,01,11};//Movement pattern for bishop
		this.chessBoard=chessBoardIn;//Reference copy of chess board
//		getMoves();
	}

	@Override
	public void getMoves() {
		for(int change:movement)
		{
			nonRecursiveGetMoves(change);
		}
	}
	
	@Override
	public String accept(ChessPieceVisitor v){
		return v.visit(this);
	}
}
