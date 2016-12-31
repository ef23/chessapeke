package data;

import java.util.*;
import json.*;

public class Pawn extends ChessPiece {

	private int[] attackMovement=new int[]{11};//attack pattern for pawns
	public Pawn(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="pawn";
		position=positionIn;
		color=colorIn;
		multiple=false;
		movement=new int[]{10};//Movement pattern for pawn
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
		validMoves=new ArrayList<String>();
		for(int change:movement)
		{
			int row=ChessPiece.getRow(position.getSpace())+ChessPiece.getRow(change);//ones position: row
			int col=ChessPiece.getCol(position.getSpace())+ChessPiece.getCol(change);//tens position: column
			int newRowCol=ChessPiece.createCoordinates(row,col);//Row/col we are checking
			//check if there is a piece at (row,col) and if row col is out of bounds
			if(chessBoard.getPosition(new Space(newRowCol))==null&&newRowCol!=-1)
				validMoves.add(position.getSpace()+" "+newRowCol);
		}
		for(int change:attackMovement)
		{
			int row=ChessPiece.getRow(position.getSpace())+ChessPiece.getRow(change);
			int colAdd=ChessPiece.getCol(position.getSpace())+ChessPiece.getCol(change);
			int colMinus=ChessPiece.getCol(position.getSpace())-ChessPiece.getCol(change);
			int rowColAdd=ChessPiece.createCoordinates(row,colAdd);//If out of bounds, set to -1 for easy detection
			//check if there is a piece at (row,col) and if row col is out of bounds
			if(rowColAdd!=-1&&chessBoard.getPosition(new Space(rowColAdd))!=null&&
					!this.spaceContainsColor(rowColAdd))
				validMoves.add(position.getSpace()+" "+rowColAdd);
			int rowColMinus=ChessPiece.createCoordinates(row,colMinus);//If out of bounds, set to -1 for easy detection
			//check if there is a piece at (row,col) and if row col is out of bounds
			if(rowColMinus!=-1&&chessBoard.getPosition(new Space(rowColMinus))!=null&&
					!this.spaceContainsColor(rowColAdd))
				validMoves.add(position.getSpace()+" "+rowColMinus);
		}
	}
}
