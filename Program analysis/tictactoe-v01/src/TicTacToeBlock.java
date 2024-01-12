/**
 * The TicTacToeBlock class represents a given block in the game.
 */
public class TicTacToeBlock
{
    /**
     * The game that contains this block
     */
    private TicTacToeGame game;

    /**
     * The current value of the contents of this block
     */
    private String contents;

    /**
     * Whether or not it is currently legal to move into this block
     */
    private boolean isLegalMove;

    /**
     * Creates a new block that will be contained in the given game.
     *
     * @param game The game that will contain the new block
     * @throws IllegalArgumentException When the given game is null
     */
    public TicTacToeBlock(TicTacToeGame game) {
	super();

	if (game == null) {
	    throw new IllegalArgumentException("The game must be non-null.");
	}
	
	this.game = game;
	this.reset();
    }

    public TicTacToeGame getGame() {
	return this.game;
    }

    /**
     * Sets the contents of this block to the given value.
     *
     * @param value The new value for the contents of this block
     * @throws IllegalArgumentException When the given value is null
     */
    public void setContents(String value) {
        Logger.log("manipulates");
	if (value == null) {
	    throw new IllegalArgumentException("The value must be non-null.");
	}
	this.contents = value;
    }

    /**
     * Returns the non-null String value of the contents of this block.
     *
     * @return The non-null String value
     */
    public String getContents() {
	return this.contents;
    }

    public void setIsLegalMove(boolean isLegalMove) {
	this.isLegalMove = isLegalMove;
    Logger.log("manipulates");
    }

    public boolean getIsLegalMove() {
	return this.isLegalMove;
    }

    /**
     * Resets this block before starting a new game.
     */
    public void reset() {
	this.contents = "";
    Logger.log("manipulates");
	this.isLegalMove = false;
    Logger.log("manipulates");
    }
}
