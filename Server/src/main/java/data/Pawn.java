package data;

import java.util.*;
import json.*;

public class Pawn extends ChessPiece {

	private int attackMovement=11;//attack pattern for pawns
	public Pawn(Space Position, ChessBoard chessBoard, boolean color){
		position=Position;
		movement=new int[]{10};//Movement pattern for pawn
		chessBoard=chessBoard;//Reference copy of chess board
	}
	@Override
	public updateBoard move(Space newPosition) {
		//changes position to new position
		position=newPosition;
		return null;//Will be changed
	
	}

	@Override
	public ArrayList<Space> getMoves(Space position){
		ArrayList<Space> validMoves=new ArrayList<Space>();
		for(int change:movement)
		{
			int row=(position.getSpaceName()%10)+(change%10);
			int col=(position.getSpaceName()/10)+(change/10);
			int newRowCol=row+(col*10);//Row/col we are checking
			if(chessBoard.getPosition(new Space(newRowCol))==null)
				validMoves.add(new Space(newRowCol));
		}
	}
}
