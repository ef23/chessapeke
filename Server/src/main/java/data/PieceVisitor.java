package data;

public interface PieceVisitor {
	public String accept(ChessPieceVisitor c);	
}
