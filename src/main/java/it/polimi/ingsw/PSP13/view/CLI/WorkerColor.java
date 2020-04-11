package it.polimi.ingsw.PSP13.view.CLI;

public enum WorkerColor {

    Blue("\u001B[34m"), Red("\u001B[31m"), Yellow("\u001B[33m");

    private final String color;
    private final String builder = "\uE77B";
    private final String reset = "\u001b[0m";

    /**
     * creates an Enum that represents Unicode strings for the builder in various colors
     * @param color
     */
    WorkerColor(String color)
    {
        this.color = color;
    }

    public String toString()
    {
        return color + builder + reset;
    }
}
