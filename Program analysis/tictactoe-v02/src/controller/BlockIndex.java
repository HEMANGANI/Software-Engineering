package controller;


public class BlockIndex 
{
    private int row;

    private int column;


    public BlockIndex(int row, int column) {
	super();

	this.row = row;
	this.column = column;
    }

    public int getRow() {
	return this.row;
    }

    public int getColumn() {
	return this.column;
    }
}