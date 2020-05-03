package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.network.client_dispatching.behavior.*;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.HashMap;
import java.util.Map;

public class MsgMap {

    private Map<Integer, ClientDispatcherBehavior> dispatcher;
    private Input input;

    public MsgMap(Input input)
    {
        this.input = input;
        init();
    }

    private void init()
    {
        dispatcher = new HashMap<>();
        dispatcher.put(0,new ClientMoveBehavior(input));
        dispatcher.put(1,new ClientBuildBehavior(input));
        dispatcher.put(2,new ClientNickBehavior(input));
        dispatcher.put(3,new ClientGodBehavior(input));
        dispatcher.put(4,new ClientSetupBehavior(input));
        dispatcher.put(5,new ClientGodSelBehavior(input));
        dispatcher.put(6,new ClientEffectBehavior(input));
        dispatcher.put(7,new ClientChooseBuilderBehavior(input));
        dispatcher.put(8,new ClientRmvBlockBehavior(input));
        dispatcher.put(9, new ClientEndGameBehavior(input));
        dispatcher.put(11,new ClientEffectDescriptionBehaviour(input));
        dispatcher.put(10,new ClientDisconnectionBehavior(input));
        dispatcher.put(13, new ClientPlayerNumBehavior(input));
        dispatcher.put(14, new ClientLeftOutBehavior(input));
    }


    public void dispatch(MessageCV msg)
    {
        ClientDispatcherBehavior behavior = dispatcher.get(msg.getId());

        behavior.behavior(msg);
    }






}
