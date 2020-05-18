package it.polimi.ingsw.PSP13.controllerTests;

import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import org.junit.BeforeClass;

import java.util.HashMap;

public class TurnHandlerTest {

    private static TurnHandler turnHandler;

    @BeforeClass
    public static void init(){

        turnHandler = new TurnHandler(new VirtualView(new HashMap<>()));

    }
}
