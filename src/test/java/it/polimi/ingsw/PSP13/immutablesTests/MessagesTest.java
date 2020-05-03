package it.polimi.ingsw.PSP13.immutablesTests;

import it.polimi.ingsw.PSP13.network.client_callback.MessageVC;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessagesTest {

    private static MessageCV cv;
    private static MessageVC vc;

    @BeforeClass
    public static void init()
    {
        cv = new MessageCV(1,false,"test",null,null,true,5);
        vc = new MessageVC(1,"test",null,0);
    }

    @Test
    public void VCTest()
    {
        assertNull(vc.getCoords());
        assertEquals(vc.getString(), "test");
        assertEquals(vc.getId(), 1);

    }

    @Test
    public void CVTest()
    {
        assertNull(cv.getCoordsList());
        assertNull(cv.getStringList());
        assertEquals(cv.getString(), "test");
        assertEquals(cv.getId(), 1);
        assertFalse(cv.isError());
        assertFalse(!cv.isCallNumber());
        assertEquals(cv.getGodsNumber(),5);

    }


}
