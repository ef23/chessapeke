package data;

/**
 * Represents a space on the chessboard
 */
public class Space {
	private int spaceName;
	
	/**
	 * Constructs a space 
	 * @param spaceName
	 * 			the coordinates of this space, starting at 1
	 *           ex. 74,11
	 */
	public Space (int spaceName){
		this.spaceName = spaceName;
	}
	
	/**
	 * Returns the spaceName of this space
	 * @return
	 * 		the string that represents the space
	 */
	public int getSpaceName(){
		return spaceName;
	}
	
	/**
	 * Changes the space on the board
	 * @param newSpace
	 * 		the new space to be set for this instance
	 */
	public void setSpaceName(Space newSpace){
		this.spaceName = newSpace.getSpaceName();
	}
	
}
