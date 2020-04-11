package it.polimi.ingsw.PSP13.immutables;

import it.polimi.ingsw.PSP13.model.board.Cell;

public class MapVM {

    private final CellVM[][] matrix = new CellVM[5][5];

    /**
     * Creates an immutable matrix of cells
     * @param matrix
     */
    public MapVM(Cell[][] matrix)
    {
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                this.matrix[i][j] = new CellVM(matrix[i][j]);
            }
        }
    }

    public CellVM[][] getMap()
    {
        return matrix;
    }



}