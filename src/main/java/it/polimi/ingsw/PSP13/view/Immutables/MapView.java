package it.polimi.ingsw.PSP13.view.Immutables;

import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.view.Immutables.CellView;

public class MapView {

    private final CellView[][] matrix = new CellView[5][5];

    /**
     * creates an immutable matrix of cells
     * @param matrix
     */
    public MapView(Cell[][] matrix)
    {
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                this.matrix[i][j] = new CellView(matrix[i][j]);
            }
        }
    }

    public CellView[][] getMap()
    {
        return matrix;
    }



}