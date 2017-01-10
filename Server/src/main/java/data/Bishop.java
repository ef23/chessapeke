package data;


public class Bishop extends ChessPiece {
	public Bishop(Space positionIn, ChessBoard chessBoardIn, boolean colorIn){
		pieceType="bishop";
		position=positionIn;
		color=colorIn;
		isMoved=false;
		movement=new int[]{11};//Movement pattern for bishop
		this.chessBoard=chessBoardIn;//Reference copy of chess board
		getMoves();
	}
	

	@Override
	public void getMoves(){
		for(int change:movement)
		{
			startRecursiveGetMoves(change);
		}
	}
}
