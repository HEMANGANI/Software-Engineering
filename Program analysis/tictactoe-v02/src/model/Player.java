package model;


public enum Player {
    PLAYER_1(1), PLAYER_2(2);

    public static final String LABEL_PREFIX = "Player ";

    private int id;


    private Player(int id) {
	this.id = id;
    }

    public String getLabel() {
	return LABEL_PREFIX + this.getID();
    }

    public int getID() {
	return this.id;
    }
}
