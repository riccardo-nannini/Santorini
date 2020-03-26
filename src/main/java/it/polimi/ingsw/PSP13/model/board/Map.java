package it.polimi.ingsw.PSP13.model.board;

public class Map {

    private Cell[][] matrix;

    public Cell[][] getMatrix() {
        return matrix;
    }

    public Map()
    {
        matrix = new Cell[5][5];
    }

}
