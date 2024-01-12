package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.Player;
import model.RowGameModel;
import controller.RowGameController;

public class RowGameGUI implements RowGameView {
    public JFrame gui = new JFrame("Tic Tac Toe");
    // This GUI applies the Composite design pattern.
    // The GUI is the Composite. The following two 
    // Views are the Components.
    public RowBoardGameView boardGameView;
    public RowGameStatusView statusView;
    public JButton reset = new JButton("Reset");

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController controller) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(3,3));
        gamePanel.add(game, BorderLayout.CENTER);

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        statusView = new RowGameStatusView(messages);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });

	boardGameView = new RowBoardGameView(game, controller);
    }

    public void update(RowGameModel gameModel) {
	boardGameView.update(gameModel);
	statusView.update(gameModel);
    }
}
