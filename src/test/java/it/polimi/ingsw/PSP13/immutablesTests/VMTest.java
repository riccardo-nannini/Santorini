package it.polimi.ingsw.PSP13.immutablesTests;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.CellVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VMTest {

    private static BuilderVM builder;
    private static CellVM cell;
    private static MapVM map;

    @BeforeClass
    public static void init()
    {
        Coords[] coords = new Coords[2];
        coords[0] = new Coords(1,1);
        coords[1] = new Coords(2,2);
        builder = new BuilderVM(coords, Color.Blue);

        cell = new CellVM(new Cell(3,3));

        Cell[][] matrix = new Cell[5][5];
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                matrix[i][j] = new Cell(i,j);
            }
        }
        map = new MapVM(matrix);

    }

    @Test
    public void BuilderTest()
    {
        Color color = builder.getColor();
        assertEquals(color,Color.Blue);

        Coords[] actual = builder.getBuilders();
        Coords[] coords = new Coords[2];
        coords[0] = new Coords(1,1);
        coords[1] = new Coords(2,2);
        assertEquals(coords[0],actual[0]);
        assertEquals(coords[1],actual[1]);

    }

    @Test
    public void CellTest()
    {
        boolean result = cell.getDome();
        assertFalse(result);

        Level level = cell.getLevel();
        assertEquals(level,Level.Floor);

    }

    @Test
    public void MapTest()
    {
        CellVM[][] matrix = new CellVM[5][5];
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                matrix[i][j] = new CellVM(new Cell(i,j));
                assertEquals(matrix[i][j],map.getMap()[i][j]);
            }
        }
    }



}
