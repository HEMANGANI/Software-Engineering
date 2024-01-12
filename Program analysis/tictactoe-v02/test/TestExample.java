import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.Player;
import model.RowBlockModel;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;

    @Before
    public void setUp() {
	game = new RowGameController();
    }

    @After
    public void tearDown() {
	game = null;
    }

    private void checkInitialConfiguration() {
	// Check the post-conditions for the new RowGameModel
	assertNotNull(game.gameModel);
	// The first player has the initial move
	assertEquals (Player.PLAYER_1, game.gameModel.getPlayer());
	// There are initially 9 possible moves left to make
	assertEquals (9, game.gameModel.getMovesLeft());
	// Each block is empty and a legal move
	for (int row = 0; row < 3; row++) {
	    for (int col = 0; col < 3; col++) {
		assertEquals("", game.gameModel.blocksData[row][col].getContents());
		assertEquals(true, game.gameModel.blocksData[row][col].getIsLegalMove());
	    } // end for col
	} // end for row
	// The final result is null
	assertNull(game.gameModel.getFinalResult());
    }

    @Test
    public void testNewGame() {
	// The setup method created a new empty game board
	this.checkInitialConfiguration();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	RowBlockModel block = new RowBlockModel(null);
    }

    @Test
    public void testLegalMove() {
	// In the setUp method, the RowGameController constructor
	// created a new RowGameModel.
	//
	// Check the pre-conditions (i.e. class invariants)
	this.checkInitialConfiguration();
	// Execute the method under test
	game.move(game.gameView.boardGameView.blocks[0][0]);
	// Check the post-conditions (i.e. class invariants)
	assertEquals(Player.PLAYER_2, game.gameModel.getPlayer());
	assertEquals(8, game.gameModel.getMovesLeft());
	assertEquals("X", game.gameModel.blocksData[0][0].getContents());
	assertEquals(false, game.gameModel.blocksData[0][0].getIsLegalMove());
    }

    @Test
    public void testIllegalMove() {
        // In the setUp method, the RowGameController constructor
        // created a new RowGameModel.
        //
        // Check the pre-conditions (i.e. class invariants)
	//
	// Execute a legal move
        game.move(game.gameView.boardGameView.blocks[0][0]);
        assertEquals(Player.PLAYER_2, game.gameModel.getPlayer());
        assertEquals(8, game.gameModel.getMovesLeft());
        assertEquals("X", game.gameModel.blocksData[0][0].getContents());
        assertEquals(false, game.gameModel.blocksData[0][0].getIsLegalMove());
	// Execute an illegal move
	game.move(game.gameView.boardGameView.blocks[0][0]);
	// Check the post-conditions (i.e. class invariants)
        assertEquals(Player.PLAYER_2, game.gameModel.getPlayer());
        assertEquals(8, game.gameModel.getMovesLeft());
        assertEquals("X", game.gameModel.blocksData[0][0].getContents());
        assertEquals(false, game.gameModel.blocksData[0][0].getIsLegalMove());
    }
}
