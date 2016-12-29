package data;

/**
 * Represents a space on the chessboard
 */
public class Space {
	private String spaceName;
	
	/**
	 * Constructs a space 
	 * @param spaceName
	 * 			the name of this space, ex. D5, E4
	 */
	public Space (String spaceName){
		this.spaceName = spaceName;
	}
	
	/**
	 * Returns the spaceName of this space
	 * @return
	 * 		the string that represents the space
	 */
	public String getSpaceName(){
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
