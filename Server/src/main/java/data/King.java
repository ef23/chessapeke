package data;

import json.updateBoard;

public class King extends ChessPiece {

	public King(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="king";
		position=positionIn;
		color=colorIn;
		multiple=false;
		movement=new int[]{10,01,11};//Movement pattern for bishop
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
		// TODO Auto-generated method stub

	}

}
