import controller.RowGameController;

public class RowGameApp 
{
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        RowGameController game = new RowGameController();
        game.gameView.gui.setVisible(true);
    }
}