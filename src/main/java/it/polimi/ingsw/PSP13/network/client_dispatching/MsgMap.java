package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_dispatching.behavior.*;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.PSP13.network.MessageID.*;

public class MsgMap {

    private Map<MessageID, ClientDispatcherBehavior> dispatcher;
    private Input input;

    public MsgMap(Input input)
    {
        this.input = input;
        init();
    }

    private void init()
    {
        dispatcher = new HashMap<>();
        dispatcher.put(move,new ClientMoveBehavior(input));
        dispatcher.put(build,new ClientBuildBehavior(input));
        dispatcher.put(processNickname,new ClientNickBehavior(input));
        dispatcher.put(processGodChoice,new ClientGodBehavior(input));
        dispatcher.put(builderSetupPhase,new ClientSetupBehavior(input));
        dispatcher.put(processGodsSelection,new ClientGodSelBehavior(input));
        dispatcher.put(useEffect,new ClientEffectBehavior(input));
        dispatcher.put(selectBuilder,new ClientChooseBuilderBehavior(input));
        dispatcher.put(removeBlock,new ClientRmvBlockBehavior(input));
        dispatcher.put(gameOver, new ClientEndGameBehavior(input));
        dispatcher.put(disconnection,new ClientDisconnectionBehavior(input));
        dispatcher.put(effectDescription,new ClientEffectDescriptionBehaviour(input));
        dispatcher.put(clientStarter, new ClientStarterBehaviour(input));
        dispatcher.put(processPlayersNumber, new ClientPlayerNumBehavior(input));
        dispatcher.put(lobbyFull, new ClientWaitQueueBehavior(input));
    }


    public void dispatch(MessageFromControllerToView msg)
    {
        ClientDispatcherBehavior behavior = dispatcher.get(msg.getMessageID());

        behavior.behavior(msg);
    }






}
