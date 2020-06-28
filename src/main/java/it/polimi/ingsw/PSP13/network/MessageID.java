package it.polimi.ingsw.PSP13.network;

public enum MessageID {

    /**
     * a list of the protocol names used in the controller-view communication and vice versa
     */
    move,build,processNickname,processGodChoice,builderSetupPhase,processGodsSelection,
    useEffect,selectBuilder,removeBlock,gameOver,disconnection,effectDescription,clientStarter,
    processPlayersNumber,lobbyFull,updateStarter,rematch,turnEnded

}
