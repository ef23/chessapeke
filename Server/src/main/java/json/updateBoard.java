package json;

import java.util.LinkedList;

public class updateBoard {
	LinkedList<PieceUpdate> update;

	/**
	 * @param update
	 */
	public updateBoard(LinkedList<PieceUpdate> update) {
		super();
		this.update = update;
	}

	/**
	 * @return the update
	 */
	public LinkedList<PieceUpdate> getUpdate() {
		return update;
	}	
}
