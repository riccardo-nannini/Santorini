package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.controller.dispatchBehavior.*;
import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

import java.net.Socket;
import java.util.EnumMap;

import static it.polimi.ingsw.PSP13.network.MessageID.*;

public class MsgDispatcher {

    private EnumMap<MessageID, ServerDispatchBehavior> behaviourMap;
    private PermaLobby lobby;
    private ViewObserver viewObserver;
    private Socket socket;

    public MsgDispatcher(PermaLobby lobby, ViewObserver viewObserver, Socket socket) {
        this.lobby = lobby;
        this.viewObserver = viewObserver;
        this.socket = socket;

        init();
    }

    /**
     * Initializes the dispatcher map with all the dispatchBehavior classes
     */
    private void init() {
        behaviourMap = new EnumMap<>(MessageID.class);
        behaviourMap.put(move,new MoveDispatch(lobby,viewObserver));
        behaviourMap.put(build,new BuildDispatch(lobby, viewObserver));
        behaviourMap.put(processNickname,new NicknameDispatch(lobby, viewObserver, socket));
        behaviourMap.put(processGodChoice,new GodChoiceDispatch(lobby, viewObserver));
        behaviourMap.put(builderSetupPhase,new BuilderSetupDispatch(lobby, viewObserver));
        behaviourMap.put(processGodsSelection,new GodsSelectionDispatch(lobby, viewObserver));
        behaviourMap.put(useEffect,new EffectDispatch(lobby, viewObserver));
        behaviourMap.put(selectBuilder,new SelectBuilderDispatch(lobby, viewObserver));
        behaviourMap.put(removeBlock,new RemoveBlockDispatch(lobby, viewObserver));
        behaviourMap.put(processPlayersNumber,new PlayersNumDispatch(lobby,viewObserver));
        behaviourMap.put(updateStarter,new StarterDispatch(lobby, viewObserver));
        behaviourMap.put(rematch, new RematchDispatch(lobby, viewObserver, socket));
        behaviourMap.put(gameOver, new HearthbeatDispatch(lobby, viewObserver));
    }

    /**
     * This methods finds the behavior related to the message protocol and starts its execution
     * @param msg the message to decode
     */
    public void dispatch(MessageFromViewToController msg) {
        ServerDispatchBehavior dispatcher = behaviourMap.get(msg.getMessageID());
        dispatcher.behavior(msg);
    }

}
