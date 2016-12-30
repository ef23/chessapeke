package data;

import java.util.*;
import json.*;

public class Pawn extends ChessPiece {

	private int[] attackMovement=new int[]{11};//attack pattern for pawns
	public Pawn(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="pawn";
		position=positionIn;
		color=colorIn;
		movement=new int[]{10};//Movement pattern for pawn
		this.chessBoard=chessBoardIn;//Reference copy of chess board
	}
	@Override
	public updateBoard move(Space newPosition) {
		//changes position to new position
		position=newPosition;
		return null;//Will be changed
	}

	@Override
	public void getMoves(){
		validMoves=new ArrayList<String>();
		for(int change:movement)
		{
			int row=(position.getSpace()%10)+(change%10);
			int col=(position.getSpace()/10)+(change/10);
			int newRowCol=row+(col*10);//Row/col we are checking
			if(chessBoard.getPosition(new Space(newRowCol))==null&&!isOutOfBounds(newRowCol))
				validMoves.add(position.getSpace()+" "+newRowCol);
		}
		for(int change:attackMovement)
		{
			int row=(position.getSpace()%10)+(change%10);
			int col1=(position.getSpace()/10)+(change/10);
			int col2=(position.getSpace()/10)-(change/10);
			int newRowCol1=row+(col1*10);
			if(chessBoard.getPosition(new Space(newRowCol1))!=null&&!isOutOfBounds(newRowCol1))
				validMoves.add(position.getSpace()+" "+newRowCol1);
			int newRowCol2=row+(col2*10);
			if(chessBoard.getPosition(new Space(newRowCol2))!=null&&!isOutOfBounds(newRowCol2))
				validMoves.add(position.getSpace()+" "+newRowCol2);
		}
	}
}
