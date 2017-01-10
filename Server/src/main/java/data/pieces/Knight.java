package data.pieces;

import data.ChessBoard;
import data.ChessPieceVisitor;
import data.PieceVisitor;
import data.Space;

public class Knight extends ChessPiece implements PieceVisitor{

	public Knight(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="knight";
		position=positionIn;
		color=colorIn;
		isMoved=false;
		movement=new int[]{21,12};//Movement pattern for bishop
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
