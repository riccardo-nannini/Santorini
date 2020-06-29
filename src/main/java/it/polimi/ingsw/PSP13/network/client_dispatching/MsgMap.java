package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_dispatching.behavior.*;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.PSP13.network.MessageID.*;

public class MsgMap {

    private EnumMap<MessageID,ClientDispatcherBehavior> dispatcher;
    private Input input;
    private UpdateListener updateListener;

    public MsgMap(Input input, UpdateListener updateListener)
    {
        this.updateListener = updateListener;
        this.input = input;
        init();
    }

    /**
     * initializes the dispatcher map
     * with all the dispatchBehavior classes
     */
    private void init()
    {
        dispatcher = new EnumMap<>(MessageID.class);
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
        dispatcher.put(disconnection,new ClientDisconnectionBehavior(input, updateListener));
        dispatcher.put(effectDescription,new ClientEffectDescriptionBehaviour(input));
        dispatcher.put(clientStarter, new ClientStarterBehaviour(input));
        dispatcher.put(processPlayersNumber, new ClientPlayerNumBehavior(input));
        dispatcher.put(lobbyFull, new ClientWaitQueueBehavior(input));
        dispatcher.put(turnEnded,new ClientTurnEndedBehavior(input));
    }

    /**
     * this methods finds the behavior related to the message protocol
     * and starts its execution
     * @param msg the message to decode
     */
    public void dispatch(MessageFromControllerToView msg)
    {
        ClientDispatcherBehavior dispatcherBehavior = dispatcher.get(msg.getMessageID());
        dispatcherBehavior.behavior(msg);
    }






}
