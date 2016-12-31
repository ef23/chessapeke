package data;

import json.updateBoard;

public class Knight extends ChessPiece {

	public Knight(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="knight";
		position=positionIn;
		color=colorIn;
		multiple=false;
		movement=new int[]{21,12};//Movement pattern for bishop
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
			int rowAdd=ChessPiece.getRow(position.getSpace())+(ChessPiece.getRow(change));//Adding to position; row
			int rowMinus=ChessPiece.getRow(position.getSpace())-(ChessPiece.getRow(change));//Subtracting from position; row
			int colAdd=ChessPiece.getCol(position.getSpace())+(ChessPiece.getCol(change));//Adding to position; column
			int colMinus=ChessPiece.getCol(position.getSpace())-(ChessPiece.getCol(change));//Subtracting from position; column
			int[] positions=new int[]{createCoordinates(rowAdd,colAdd),createCoordinates(rowAdd,colMinus),
					createCoordinates(rowMinus,colAdd),createCoordinates(rowMinus,colMinus)};
			for(int rowCol:positions)
			{
				if(rowCol!=-1&&!this.spaceContainsColor(rowCol))
				{//If not out of bounds and space does not contain the same color, add to list of valid moves
					validMoves.add(position.getSpace()+" "+rowCol);
				}
			}
		}
	}

}
