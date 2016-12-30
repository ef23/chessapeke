package data;

/**
 * Represents a space on the chessboard
 */
public class Space {
	private int space;
	
	/**
	 * Constructs a space 
	 * @param spaceName
	 * 			the coordinates of this space, starting at 1
	 *           ex. 74,11
	 */
	public Space (int spaceName){
		this.space = spaceName;
	}
	
	/**
	 * Returns the spaceName of this space
	 * @return
	 * 		the string that represents the space
	 */
	public int getSpace(){
		return space;
	}
	
	/**
	 * Changes the space on the board
	 * @param newSpace
	 * 		the new space to be set for this instance
	 */
	public void setSpace(Space newSpace){
		this.space = newSpace.getSpace();
	}
	
	
	@Override
	public boolean equals (Object o){
		if (o instanceof Space){
			Space s = (Space) o;
			return space == s.getSpace();
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return (space + "").hashCode();
	}
	
}
