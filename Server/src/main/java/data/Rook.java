package data;

import json.updateBoard;

public class Rook extends ChessPiece {

	public Rook(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="rook";
		position=positionIn;
		color=colorIn;
		multiple=true;
		movement=new int[]{10,01};//Movement pattern for bishop
		this.chessBoard=chessBoardIn;//Reference copy of chess board
		getMoves();
	}
	
	@Override
	public updateBoard move(Space newPosition) {
		//changes position to new position
		position=newPosition;
		return null;//Will be changed
	}
	
	@Override
	public void getMoves() {
		for(int change:movement)
		{
			startRecursiveGetMoves(change);
		}
	}

}
