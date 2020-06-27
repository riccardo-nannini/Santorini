package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Coords;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

public class MapTest {

    private static Map map;

    @BeforeClass
    public static void setup()
    {
        map = new Map();
    }

    @Test
    public void checkIfLegal_LegalCoords_ExpectedAllLegal()
    {
        boolean result;

        result = map.isLegal(new Coords(0,0));
        assertTrue(result);

        result = map.isLegal(new Coords(0,4));
        assertTrue(result);

        result = map.isLegal(new Coords(4,0));
        assertTrue(result);

        result = map.isLegal(new Coords(4,4));
        assertTrue(result);

        result = map.isLegal(new Coords(3,2));
        assertTrue(result);

    }

    @Test
    public void checkIfLegal_NotLegalCoords_ExpectedAllNotLegal()
    {
        boolean result;

        result = map.isLegal(new Coords(-1,3));
        assertFalse(result);

        result = map.isLegal(new Coords(3,-1));
        assertFalse(result);

        result = map.isLegal(new Coords(-1,-1));
        assertFalse(result);

        result = map.isLegal(new Coords(5,5));
        assertFalse(result);

        result = map.isLegal(new Coords(0,5));
        assertFalse(result);

        result = map.isLegal(new Coords(5,0));
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCell_NotLegalCoords_ShouldThrowException()
    {
        map.getCell(new Coords(-1,-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAdjacent_NotLegalCoords_ShouldThrowException()
    {
        map.getAdjacent(new Coords(-1,-1));
    }

    @Test
    public void getAdjacents_LegalCoords_CorrectBehaviour()
    {
        List<Coords> list;

        list = map.getAdjacent(new Coords(0,0));
        assertThat(list, hasItems(new Coords(0,1)));
        assertThat(list, hasItems(new Coords(1,0)));
        assertThat(list, hasItems(new Coords(1,1)));
        assertEquals(list.size(),3);


        list = map.getAdjacent(new Coords(4,4));
        assertThat(list, hasItems(new Coords(4,3)));
        assertThat(list, hasItems(new Coords(3,4)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertEquals(list.size(),3);

        list = map.getAdjacent(new Coords(4,0));
        System.out.println(list);
        assertThat(list, hasItems(new Coords(4,1)));
        assertThat(list, hasItems(new Coords(3,0)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertEquals(list.size(),3);


        list = map.getAdjacent(new Coords(0,4));
        assertThat(list, hasItems(new Coords(0,3)));
        assertThat(list, hasItems(new Coords(1,3)));
        assertThat(list, hasItems(new Coords(1,4)));
        assertEquals(list.size(),3);


        list = map.getAdjacent(new Coords(0,2));
        assertThat(list, hasItems(new Coords(0,1)));
        assertThat(list, hasItems(new Coords(0,3)));
        assertThat(list, hasItems(new Coords(1,1)));
        assertThat(list, hasItems(new Coords(1,2)));
        assertThat(list, hasItems(new Coords(1,3)));
        assertEquals(list.size(),5);


        list = map.getAdjacent(new Coords(4,2));
        assertThat(list, hasItems(new Coords(4,1)));
        assertThat(list, hasItems(new Coords(4,3)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertThat(list, hasItems(new Coords(3,2)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertEquals(list.size(),5);


        list = map.getAdjacent(new Coords(2,0));
        assertThat(list, hasItems(new Coords(1,0)));
        assertThat(list, hasItems(new Coords(3,0)));
        assertThat(list, hasItems(new Coords(1,1)));
        assertThat(list, hasItems(new Coords(2,1)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertEquals(list.size(),5);


        list = map.getAdjacent(new Coords(2,4));
        assertThat(list, hasItems(new Coords(1,4)));
        assertThat(list, hasItems(new Coords(3,4)));
        assertThat(list, hasItems(new Coords(1,3)));
        assertThat(list, hasItems(new Coords(2,3)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertEquals(list.size(),5);


        list = map.getAdjacent(new Coords(3,2));
        assertThat(list, hasItems(new Coords(2,1)));
        assertThat(list, hasItems(new Coords(2,2)));
        assertThat(list, hasItems(new Coords(2,3)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertThat(list, hasItems(new Coords(4,1)));
        assertThat(list, hasItems(new Coords(4,2)));
        assertThat(list, hasItems(new Coords(4,3)));
        assertEquals(list.size(),8);

    }
}
