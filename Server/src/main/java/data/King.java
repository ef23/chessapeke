package data;


public class King extends ChessPiece {

	public King(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="king";
		position=positionIn;
		color=colorIn;
		movement=new int[]{10,01,11};//Movement pattern for bishop
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
