package model;

import logger.Logger;

public class RowGameModel 
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData = new RowBlockModel[3][3];

    /**
     * The current player taking their turn
     */
    private Player player = Player.PLAYER_1;
    private int movesLeft = 9;

    private String finalResult = null;


    public RowGameModel() {
	super();

	for (int row = 0; row < 3; row++) {
	    for (int col = 0; col < 3; col++) {
		blocksData[row][col] = new RowBlockModel(this);
	    } // end for col
	} // end for row
    }

    public Player getPlayer() {
	return this.player;
    }

    public void setPlayer(Player player) {
        Logger.log("manipulates");
	if (player == null) {
	    throw new IllegalArgumentException("The player must be non-null.");
	}
	this.player = player;
    }

    public int getMovesLeft() {
	return this.movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        Logger.log("manipulates");
	this.movesLeft = movesLeft;
    }

    public String getFinalResult() {
	return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
        Logger.log("manipulates");
	this.finalResult = finalResult;
    }
}
