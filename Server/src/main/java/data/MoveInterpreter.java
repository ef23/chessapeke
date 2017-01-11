package data;

public class MoveInterpreter {

	/**
	 * 
	 * @param validMove
	 * 		move information stored in format "<pieceType> <color>|<startLocation> <endLocation>"
	 * @return
	 * 		color of piece that is moving in this move, white=true,black=false
	 */
	public static String findColor(String validMove)
	{
		return (validMove.split("|")[0].split(" ")[1]).equals("true")?"white":"black";
	}
	
	/**
	 * 
	 * @param validMove
	 * 		move information stored in format "<pieceType> <color>|<startLocation> <endLocation>"
	 * @return
	 * 		type of piece that is moving in given validMove, or "castle" if the move represents a castle
	 */
	public static String findPieceType(String validMove)
	{
		if(validMove.split("|")[1].equals("KC")||validMove.split("|")[1].equals("QC"))
		{//string represents castling
			return "castle";
		}
		return validMove.split("|")[0].split(" ")[0];
	}
	
	/**
	 * 
	 * @param validMove
	 * 		move information stored in format "<pieceType> <color>|<startLocation> <endLocation>"
	 * @return
	 * 		-1 if move is a castle, or location int with column in tens place, row in ones place
	 */
	public static int findStartLocation(String validMove)
	{
		if(validMove.split("|")[1].equals("KC")||validMove.split("|")[1].equals("QC"))
		{//string represents castling
			return -1;
		}
		return Integer.parseInt(validMove.split("|")[1].split(" ")[0]);
	}
	
	/**
	 * 
	 * @param validMove
	 * move information stored in format "<pieceType> <color>|<startLocation> <endLocation>"
	 * @return
	 * -1 if move is a castle, or location int with column in tens place, row in ones place
	 */
	public static int findEndLocation(String validMove)
	{
		if(validMove.split("|")[1].equals("KC")||validMove.split("|")[1].equals("QC"))
		{//string represents castling
			return -1;
		}
		return Integer.parseInt(validMove.split("|")[1].split(" ")[1]);
	}
}
