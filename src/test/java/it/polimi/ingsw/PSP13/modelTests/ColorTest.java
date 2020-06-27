package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.player.Color;

import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ColorTest {


    @Test
    public void getColor_CorrectInput_CorrectBehaviour()
    {
        List<Color> colors = Color.getColors();

        assertThat(colors, hasItems(Color.Blue));
        assertThat(colors, hasItems(Color.Yellow));
        assertThat(colors, hasItems(Color.Red));
        assertEquals(colors.size(),3);

    }


}
