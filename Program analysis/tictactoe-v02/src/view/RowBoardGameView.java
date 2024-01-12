package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import controller.RowGameController;
import logger.Logger;

public class RowBoardGameView implements RowGameView {
    public JButton[][] blocks = new JButton[3][3];


    /**
     * Creates a new RowBoardGameView.
     */
    public RowBoardGameView(JPanel game, RowGameController controller) {
        // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(75,75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
			controller.move((JButton)e.getSource());
                    }
                });
            }
        }
    }

    /**
     * Updates the block at the given row and column 
     * after one of the player's moves.
     *
     * @param gameModel The RowGameModel containing the block
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    public void updateBlock(RowGameModel gameModel, int row, int column) {
        Logger.log("updates");
	blocks[row][column].setText(gameModel.blocksData[row][column].getContents());
	blocks[row][column].setEnabled(gameModel.blocksData[row][column].getIsLegalMove());
    }

    public void update(RowGameModel gameModel) {
	for (int row = 0; row < 3; row++) {
	    for (int col = 0; col < 3; col++) {
		this.updateBlock(gameModel, row, col);
	    } // end for col
	} // end for row
    }
}
