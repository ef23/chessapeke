package json;

import java.util.LinkedList;

public class UpdateBoard {
	LinkedList<PieceUpdate> update;

	/**
	 * @param update
	 */
	public UpdateBoard(LinkedList<PieceUpdate> update) {
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
