package data;

import data.pieces.Bishop;
import data.pieces.King;
import data.pieces.Knight;
import data.pieces.Pawn;
import data.pieces.Queen;
import data.pieces.Rook;

/**
 * Visitor pattern
 */
public class ChessPieceVisitor {
	public String visit(King e){
		return "king";
	}
	public String visit(Queen e){
		return "queen";
	}
	public String visit(Bishop e){
		return "bishop";
	}
	public String visit(Knight e){
		return "knight";
	}
	public String visit(Rook e){
		return "rook";
	}
	public String visit(Pawn e){
		return "pawn";
	}
}
