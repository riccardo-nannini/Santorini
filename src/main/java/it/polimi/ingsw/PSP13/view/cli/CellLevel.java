package it.polimi.ingsw.PSP13.view.cli;

public enum CellLevel {

    Floor("\u001B[37m\u24c4"), Base("\u001B[94m\u2460"), Medium("\u001B[36m\u2461"), Top("\u001B[34m\u2462");

    String color;

    static final String RESET = "\u001b[0m";

    CellLevel(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
