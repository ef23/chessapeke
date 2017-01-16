package data;

public class Main {
	public static void main(String[] args){
		ChessBoard test = new ChessBoard();
		test.displayBoard();
		for(String s:test.validMoves)
			System.out.print(s);
		test.parseMove("pawn", new Space(38));
		test.displayBoard();
	}
}
