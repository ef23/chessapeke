package data;

import data.pieces.*;

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
	public String visit(ChessPiece e){
		return visit(e);
	}
}
