package data;

import java.util.Set;

public class Main {
	public static void main(String[] args){
		ChessBoard test = new ChessBoard();
		test.displayBoard();
		for(String move:test.validMoves)
		{
			System.out.println(move);
		}
		test.parseMove("pawn", new Space(38));
		test.displayBoard();
	}
}
