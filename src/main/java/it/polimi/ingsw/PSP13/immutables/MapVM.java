package it.polimi.ingsw.PSP13.immutables;

import it.polimi.ingsw.PSP13.model.board.Cell;

import java.io.Serializable;

public class MapVM implements Serializable {

        private static final long serialVersionUID = 673L;
        private final CellVM[][] matrix = new CellVM[5][5];

        /**
         * Creates an immutable matrix of cells
         * @param matrix the model's map
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