package it.polimi.ingsw.PSP13.view.CLI;

public enum CellLevel {

    Floor("\u001B[37m\u24c4"), Base("\u001B[94m\u2460"),
    Medium("\u001B[36m\u2461"), Top("\u001B[34m\u2462"),
    FloorHighlighted("\u001B[33m\u24c4"), BaseHighlighted("\u001B[33m\u2460"),
    MediumHighlighted("\u001B[33m\u2461"), TopHighlighted("\u001B[33m\u2462");

    String color;

    static final String RESET = "\u001b[0m";

    /**
     * Creates an Enum that represents Unicode strings for the cell
     * in various colors depending on the level
     * @param color cell's color
     */
    CellLevel(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
