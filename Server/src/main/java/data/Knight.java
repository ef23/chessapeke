package data;


public class Knight extends ChessPiece {

	public Knight(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="knight";
		position=positionIn;
		color=colorIn;
		movement=new int[]{21,12};//Movement pattern for bishop
		this.chessBoard=chessBoardIn;//Reference copy of chess board
		getMoves();
	}
	

	@Override
	public void getMoves() {
		for(int change:movement)
		{
			nonRecursiveGetMoves(change);
		}
	}

}
