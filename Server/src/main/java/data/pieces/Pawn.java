package data.pieces;

import java.util.ArrayList;

import data.ChessBoard;
import data.ChessPieceVisitor;
import data.PieceVisitor;
import data.Space;

public class Pawn extends ChessPiece implements PieceVisitor{

	private int[] attackMovement=new int[]{11};//attack pattern for pawns
	public Pawn(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="pawn";
		position=positionIn;
		isMoved=false;
		color=colorIn;
		movement=new int[]{10};//Movement pattern for pawn
		this.chessBoard=chessBoardIn;//Reference copy of chess board
	}

	@Override
	public void getMoves(){
		validMoves=new ArrayList<String>();
		for(int change:movement)
		{
			int row=ChessPiece.getRow(position.getSpace())+((this.isColor())?ChessPiece.getRow(change):-ChessPiece.getRow(change));//tens position: row
			int col=ChessPiece.getCol(position.getSpace())+ChessPiece.getCol(change);//ones position: column
			int newRowCol=ChessPiece.createCoordinates(row,col);//Row/col we are checking
			//check if there is a piece at (row,col) and if row col is out of bounds
			if(chessBoard.getPosition(new Space(newRowCol))==null&&newRowCol!=-1)
			{
				validMoves.add(this.getPieceType()+" "+this.isColor()+" "+position.getSpace()+" "+newRowCol);
			}
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
				validMoves.add(this.getPieceType()+" "+this.isColor()+" "+position.getSpace()+" "+rowColAdd+"*");//Add * to indicate capture
			int rowColMinus=ChessPiece.createCoordinates(row,colMinus);//If out of bounds, set to -1 for easy detection
			//check if there is a piece at (row,col) and if row col is out of bounds
			if(rowColMinus!=-1&&chessBoard.getPosition(new Space(rowColMinus))!=null&&
					!this.spaceContainsColor(rowColAdd))
				validMoves.add(this.getPieceType()+" "+this.isColor()+" "+position.getSpace()+" "+rowColMinus+"*");//Add * to indicate capture
		}
		//implementation for en passant
		int row=ChessPiece.getRow(position.getSpace());
		int colAdd=ChessPiece.getCol(position.getSpace())+1;
		int colMinus=ChessPiece.getCol(position.getSpace())-1;
		int rowColAdd=ChessPiece.createCoordinates(row, colAdd);
		int rowColMinus=ChessPiece.createCoordinates(row, colMinus);
		if(rowColAdd!=-1&&
				chessBoard.getPosition(new Space(rowColAdd))!=null&&
				chessBoard.getPosition(new Space(rowColAdd)).spaceContainsOppositeColor(this.position.getSpace())&&
				chessBoard.getPosition(new Space(rowColAdd)).getPieceType().equals("pawn"))
		{
			validMoves.add(this.getPieceType()+" "+this.isColor()+" "+position.getSpace()+" "+ChessPiece.createCoordinates((this.color)?row+1:row-1, colAdd)+"*"+rowColAdd);//validMove string for en passant has location of piece we are taking after *
		}
		if(rowColMinus!=-1&&
				chessBoard.getPosition(new Space(rowColMinus))!=null&&
				chessBoard.getPosition(new Space(rowColMinus)).spaceContainsOppositeColor(this.position.getSpace())&&
				chessBoard.getPosition(new Space(rowColMinus)).getPieceType().equals("pawn"))
		{
			validMoves.add(this.getPieceType()+" "+this.isColor()+" "+position.getSpace()+" "+ChessPiece.createCoordinates((this.color)?row+1:row-1, colAdd)+"*"+rowColMinus);//validMove string for en passant has location of piece we are taking after *
		}
		//implementation for first move double space movement
		int col=ChessPiece.getCol(position.getSpace());
		int rowAdd=ChessPiece.getRow(position.getSpace())+((this.isColor())?2:-2);
		int rowAddCol=ChessPiece.createCoordinates(rowAdd, col);
		if(this.hasMoved()==false&&
				rowAddCol!=-1&&
				chessBoard.getPosition(new Space(rowAddCol))==null)
		{
			validMoves.add(this.getPieceType()+" "+this.isColor()+" "+position.getSpace()+" "+rowAddCol);
		}
	}
	
	@Override
	public String accept(ChessPieceVisitor v){
		return v.visit(this);
	}
}
