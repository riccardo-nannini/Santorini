package it.polimi.ingsw.PSP13.immutablesTests;

import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import org.junit.BeforeClass;
import org.junit.Test;

import static it.polimi.ingsw.PSP13.network.MessageID.move;
import static org.junit.Assert.*;

public class MessagesTest {

    private static MessageFromControllerToView cv;
    private static MessageFromViewToController vc;

    @BeforeClass
    public static void init()
    {
        cv = new MessageFromControllerToView(move,false,"test",null,null,true,5);
        vc = new MessageFromViewToController(move,"test",null,0);
    }

    @Test
    public void VCTest()
    {
        assertNull(vc.getCoords());
        assertEquals(vc.getString(), "test");
        assertEquals(vc.getMessageID(), move);

    }

    @Test
    public void CVTest()
    {
        assertNull(cv.getCoordsList());
        assertNull(cv.getStringList());
        assertEquals(cv.getString(), "test");
        assertEquals(cv.getMessageID(), move);
        assertFalse(cv.isError());
        assertFalse(!cv.isCallNumber());
        assertEquals(cv.getGodsNumber(),5);

    }


}
