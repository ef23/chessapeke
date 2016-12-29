package json;

import data.Space;

public class PieceUpdate {
	String player;
	String piece;
	Space from;
	Space to;
	boolean isKilled;
	
	
	/**
	 * @param player
	 * @param piece
	 * @param from
	 * @param to
	 * @param isKilled
	 */
	public PieceUpdate(String player, String piece, Space from, Space to, boolean isKilled) {
		super();
		this.player = player;
		this.piece = piece;
		this.from = from;
		this.to = to;
		this.isKilled = isKilled;
	}


	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}


	/**
	 * @return the piece
	 */
	public String getPiece() {
		return piece;
	}


	/**
	 * @return the from
	 */
	public Space getFrom() {
		return from;
	}


	/**
	 * @return the to
	 */
	public Space getTo() {
		return to;
	}


	/**
	 * @return the isKilled
	 */
	public boolean isKilled() {
		return isKilled;
	}
	
	
	
}
