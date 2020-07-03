package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfo;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VirtualView {

    private final HashMap<String, ObjectOutputStream> outputMap;
    private HashMap<String, Color> colorsMap;
    private HashMap<String, String> godsMap;

    /**
     * Creates an hashMap where the keys are the usernames and the values
     * are the corresponding client's ObjectOutputStream
     * @param hashMap hash table <username,socket>
     */
    public VirtualView(HashMap<String,ObjectOutputStream> hashMap) {
        outputMap = hashMap;
        colorsMap = new HashMap<>();
        godsMap = new HashMap<>();
    }

    /**
     * Sends an updated mapVM to the clients
     * @param mapVM mapVM sent to the client
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void updateMap(MapVM mapVM) throws IOException {
        for(ObjectOutputStream output : outputMap.values()) {
            try {
                output.writeObject(mapVM);
            } catch (IOException e) {
                notifyDisconnection();
            }
        }
    }

    /**
     * Removes a player from both color and gods map after the elimination of the player from the game
     * @param player the eliminated player's username
     */
    public void removePlayer(String player) {
        colorsMap.remove(player);
        godsMap.remove(player);
    }

    /**
     * Sends an updated builderVM to the clients
     * @param builderVM builderVM sent to the client
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void updateBuilders(BuilderVM builderVM) throws IOException {
        for(ObjectOutputStream output : outputMap.values()) {
            try {
                output.writeObject(builderVM);
            } catch (IOException e) {
                notifyDisconnection();
            }
        }
    }

    /**
     * Sends to the player's client a MessageCV representing a move input request
     * @param player player username
     * @param checkMoveCells list of legal coords that can be selected by the client for the move
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void moveInput(String player, List<Coords> checkMoveCells, boolean error) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.move,error,null,checkMoveCells,null,false,-1);

        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Sends to the player's client a MessageCV representing a build input request
     * @param player player username
     * @param checkBuildCells list of legal coords that can be selected by the client for the build
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void buildInput(String player, List<Coords> checkBuildCells, boolean error) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.build,error,null,checkBuildCells,null,false,-1);
        try{
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a god input request and also a message to the opponents informing them
     * the player is selecting his god.
     * Otherwise, it sends one message to player informing him which god
     * the server assigns him , when only one god remains
     * @param player player username
     * @param chosenGods list of gods chosen by the challenger among which the player can choose his god
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void godInput(String player, List<String> chosenGods, boolean error) throws IOException {

        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.processGodChoice,error,null,null,chosenGods,false,1);

        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }

        if (chosenGods.size() > 1) {
            MessageFromControllerToView messageOpponents;
            messageOpponents =
                    new MessageFromControllerToView(MessageID.processGodChoice,false,player,null, Collections.unmodifiableList(new ArrayList<>(chosenGods)),false,-1);
            for (ObjectOutputStream output : outputMap.values()) {
                if (!output.equals(outputMap.get(player))) {
                    try{
                        output.writeObject(messageOpponents);
                    } catch (IOException e) {
                        notifyDisconnection();
                    }
                }
            }
        }
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a builder setup input request
     * @param player player username
     * @param callNumber true if is the first request (requests with error excluded)
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void builderSetUpInput(String player, boolean callNumber, boolean error) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.builderSetupPhase,error,null,null,null,callNumber,-1);

        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a god selection input request.
     * Sends also a message to the opponents informing them
     * the challenger is selecting the gods
     * @param challenger challenger player username
     * @param godsList list of all gods
     * @param godsNumber number of gods he has to choose
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void godSelectionInput(String challenger, List<String> godsList, int godsNumber, boolean error) throws IOException {
        MessageFromControllerToView message = new MessageFromControllerToView(MessageID.processGodsSelection,error,null,null,godsList,false,godsNumber);
        try {
            outputMap.get(challenger).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }

        MessageFromControllerToView messageOpponents =
                new MessageFromControllerToView(MessageID.processGodsSelection,false,challenger,null,godsList,false,0);

        for(ObjectOutputStream output : outputMap.values()) {
            if (!output.equals(outputMap.get(challenger))) {
                try {
                    output.writeObject(messageOpponents);
                } catch (IOException e) {
                    notifyDisconnection();
                }
            }
        }
    }

    /**
     * Sends to the player's client a MessageCV representing a "useEffect" request
     * @param player player username
     * @param god name of the player's god
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void effectInput(String player, String god) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.useEffect,false,god,null,null,false,-1);
        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Sends to the player's client a MessageCV representing a choose builder request
     * @param player player username
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void chooseBuilder(String player) throws IOException {
        MessageFromControllerToView message = new MessageFromControllerToView(MessageID.selectBuilder,false,player,null,null,false,-1);
        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Sends to the player's client a MessageCV representing a remove block request
     * @param player player username
     * @param removableBlocks list of legal coords that can be selected by the client for the removal
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void removeBlock(String player, List<Coords> removableBlocks, boolean error) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.removeBlock,error,null,removableBlocks,null,false,-1);
        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Notifies the client that he won the game
     * @param username the winner
     * @throws IOException
     */
    public void notifyWin(String username) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.gameOver,false,"Win",null,null,false,-1);

        try {
            outputMap.get(username).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Notifies the client that he lost the game
     * @param username the loser
     * @param ended indicates if the match ended or is still going on
     * @throws IOException
     */
    public void notifyLose(String username, boolean ended) throws IOException {
        this.removePlayer(username);
        MessageFromControllerToView message;
        if (ended) {
            message = new MessageFromControllerToView(MessageID.gameOver,false,"Lose",null,null,false,-1);

        } else {
            message = new MessageFromControllerToView(MessageID.gameOver,false,"Lose_InGame",null,null,false,-1);
        }

        try {
            outputMap.get(username).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    public void setGod(String username,String god) {
        godsMap.put(username,god);
    }

    public void setColorsMap(HashMap<String, Color> colorsMap) {
        this.colorsMap = colorsMap;
    }

    /**
     * Sends to every client a message containing information about them
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void notifyClientsInfo(HashMap<String,String> effectsMap) throws IOException {
        for (String username : colorsMap.keySet()) {
            MessageClientsInfo message = generateMessageClientsInfoCV(username, effectsMap);
            try {
                outputMap.get(username).writeObject(message);
            } catch (IOException e) {
                notifyDisconnection();
            }
        }
    }

    /**
     * @param username username of the player the message will be sent to
     * @return a MessageClientsInfoCV containing clients information
     */
    public MessageClientsInfo generateMessageClientsInfoCV(String username, HashMap<String,String> effectsMap) {
        MessageClientsInfo message = new MessageClientsInfo();
        message.setClientUsername(username);
        message.setClientColor(colorsMap.get(username));
        message.setClientGod(godsMap.get(username));
        message.setClientEffect(effectsMap.get(username));
        List<String> opponentsUsernames = new ArrayList<>();
        List<Color> opponentsColors = new ArrayList<>();
        List<String> opponentsGod = new ArrayList<>();
        List<String> opponentsEffects = new ArrayList<>();
        for (String opponentUsername : colorsMap.keySet()) {
            if (!opponentUsername.equals(username)) {
                opponentsUsernames.add(opponentUsername);
                opponentsColors.add(colorsMap.get(opponentUsername));
                opponentsGod.add(godsMap.get(opponentUsername));
                opponentsEffects.add(effectsMap.get(opponentUsername));
            }
        }
        message.setOpponentsUsernames(opponentsUsernames);
        message.setOpponentsColors(opponentsColors);
        message.setOpponentsGod(opponentsGod);
        message.setOpponentsEffects(opponentsEffects);
        return message;
    }


    /**
     * Sends to all players a message containing the gods' effect
     * @param godEffects a List containing the effects description
     */
    public void sendGodEffectDescription(List<String> godEffects) throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.effectDescription,false, null,null,godEffects,false,-1);
        for(ObjectOutputStream output : outputMap.values()) {
                try {
                    output.writeObject(message);
                } catch (IOException e) {
                    notifyDisconnection();
                }
        }
    }

    /**
     * Notifies disconnection to the clients while playing
     * @throws IOException
     */
    public void notifyDisconnection() throws IOException {
        int i = 1;
        for(ObjectOutputStream output : outputMap.values()) {
            MessageFromControllerToView message = new MessageFromControllerToView(MessageID.disconnection,false);
            try {
                output.writeObject(message);
                output.close();
            } catch (IOException e) {
                System.out.println("Cannot send disconnection message "+ i);
            } finally {
                i++;
            }
        }
        throw new IOException();
    }

    /**
     * Sends to the challenger's client a MessageCV representing a starter selection input request
     * @param challenger username of the challenger
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void starterInput(String challenger, boolean error)  throws IOException {
        MessageFromControllerToView message =
                new MessageFromControllerToView(MessageID.clientStarter,error,null,null,new ArrayList<String>(outputMap.keySet()),false,-1);
        try {
            outputMap.get(challenger).writeObject(message);
        } catch (IOException e){
            notifyDisconnection();
        }

        MessageFromControllerToView messageOpponents = new MessageFromControllerToView(MessageID.clientStarter,error,challenger,null,null,false,-1);
        for(ObjectOutputStream output : outputMap.values()) {
            if (!output.equals(outputMap.get(challenger))) {
                try {
                    output.writeObject(messageOpponents);
                } catch (IOException e) {
                    notifyDisconnection();
                }
            }
        }
    }

    /**
     * Sends to the player's client a MessageCV informing him his turn is finished
     * @param player player username
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void notifyTurnEnded(String player) throws IOException {
        MessageFromControllerToView message =  new MessageFromControllerToView(MessageID.turnEnded,false, null, null, null, false, 0);
        try {
            outputMap.get(player).writeObject(message);
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

}