package data;

import java.util.ArrayList;

import json.updateBoard;

public class Bishop extends ChessPiece {
	public Bishop(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="bishop";
		position=positionIn;
		color=colorIn;
		movement=new int[]{11};//Movement pattern for bishop
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
	public void getMoves(){
		//To be implemented
	}
}
