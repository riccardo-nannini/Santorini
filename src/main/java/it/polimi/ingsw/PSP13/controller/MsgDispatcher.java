package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.controller.dispatchBehavior.*;
import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;
import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.network.client_dispatching.behavior.*;

import java.net.Socket;
import java.util.EnumMap;

import static it.polimi.ingsw.PSP13.network.MessageID.*;

public class MsgDispatcher {

    private EnumMap<MessageID, ServerDispatchBehavior> map;
    private PermaLobby lobby;
    private ViewObserver viewObserver;
    private Socket socket;

    public MsgDispatcher(PermaLobby lobby, ViewObserver viewObserver, Socket socket) {
        this.lobby = lobby;
        this.viewObserver = viewObserver;
        this.socket = socket;

        init();
    }

    private void init() {
        map = new EnumMap<>(MessageID.class);
        map.put(move,new MoveDispatch(lobby,viewObserver));
        map.put(build,new BuildDispatch(lobby, viewObserver));
        map.put(processNickname,new NicknameDispatch(lobby, viewObserver, socket));
        map.put(processGodChoice,new GodChoiceDispatch(lobby, viewObserver));
        map.put(builderSetupPhase,new BuilderSetupDispatch(lobby, viewObserver));
        map.put(processGodsSelection,new GodsSelectionDispatch(lobby, viewObserver));
        map.put(useEffect,new EffectDispatch(lobby, viewObserver));
        map.put(selectBuilder,new SelectBuilderDispatch(lobby, viewObserver));
        map.put(removeBlock,new RemoveBlockDispatch(lobby, viewObserver));
        map.put(processPlayersNumber,new PlayersNumDispatch(lobby,viewObserver));
        map.put(updateStarter,new StarterDispatch(lobby, viewObserver));
        map.put(rematch, new RematchDispatch(lobby, viewObserver, socket));
        map.put(gameOver, new HearthbeatDispatch(lobby, viewObserver));
    }

    public void dispatch(MessageFromViewToController msg) {

        ServerDispatchBehavior dispatcher = map.get(msg.getMessageID());

        dispatcher.behavior(msg);
    }

}
