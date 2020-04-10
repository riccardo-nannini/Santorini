package it.polimi.ingsw.PSP13.model.board;

import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Cell[][] matrix;

    /**
     * initializes a new matrix
     */
    public Map()
    {
        matrix = new Cell[5][5];

        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                matrix[i][j] = new Cell(i,j);
            }
        }
    }

    /**
     *
     * @param coords
     * @return false if coords' attributes break the matrix boundaries, true if coords is a legal object
     */
    public static boolean isLegal(Coords coords)
    {
        if(coords.getX()>=5 || coords.getX()<0)
            return false;
        if(coords.getY()>=5 || coords.getY()<0)
            return false;

        return true;
    }

    /**
     *
     * @param coords
     * @return a list of legal object coords that are adjacent to the param
     * @throws IllegalArgumentException if coords is not a legal object
     */
    public List<Coords> getAdjacent(Coords coords) throws IllegalArgumentException
    {
        if(coords == null || !isLegal(coords))
            throw new IllegalArgumentException();

        List<Coords> adjacents = new ArrayList();
        int x = coords.getX()-1;
        int y = coords.getY()-1;

        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                Coords temp = new Coords(x+i,y+j);
                if(isLegal(temp))
                    adjacents.add(temp);
            }
        }

        adjacents.remove(coords);

        return adjacents;
    }

    /**
     * @param coords
     * @return the cell corresponding to the matrix coordinates coords
     * @throws IllegalArgumentException if coords is not a legal object
     */
    public Cell getCell(Coords coords) throws IllegalArgumentException
    {
        if(coords == null || !isLegal(coords))
            throw new IllegalArgumentException();

        return matrix[coords.getX()][coords.getY()];
    }

    /**
     *
     * @param coords
     * @param level
     * sets the height of the cell specified in the params
     * @throws IllegalArgumentException if coords is not a legal object
     */
    public void setCell(Coords coords, Level level) throws IllegalArgumentException
    {
        if(coords == null || !isLegal(coords))
            throw new IllegalArgumentException();

        matrix[coords.getX()][coords.getY()].setLevel(level);
    }


    public Cell[][] getMatrix() {
        return matrix;
    }


}
