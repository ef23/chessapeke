package data;

import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args){
		ChessBoard test = new ChessBoard();
		Scanner s = new Scanner(System.in);
		test.displayBoard();
		while(true){
//			for (String move : test.validMoves){
//				System.out.println(move);
//			}
			try {
				if (test.isWhiteTurn())
					System.out.println("\nWhite's turn.");
				else
					System.out.println("\nBlack's turn");
				String input = s.nextLine();
				test.doMove(input);
				test.displayBoard();
			} catch (Exception e) {
				continue;
			}
		}
	}
}
