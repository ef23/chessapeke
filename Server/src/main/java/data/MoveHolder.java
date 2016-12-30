package data;

import java.util.ArrayList;

/**
 * Associates a position with a piece and thus, the moves
 */
public class MoveHolder {
	Space position;
	ArrayList<String> moves;
	/**
	 * @param position
	 * @param moves
	 */
	public MoveHolder(Space position, ArrayList<String> moves) {
		super();
		this.position = position;
		this.moves = moves;
	}
	/**
	 * @return the position
	 */
	public Space getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Space position) {
		this.position = position;
	}
	/**
	 * @return the moves
	 */
	public ArrayList<String> getMoves() {
		return moves;
	}
	/**
	 * @param moves the moves to set
	 */
	public void setMoves(ArrayList<String> moves) {
		this.moves = moves;
	}
	
	
	
}
