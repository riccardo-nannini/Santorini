package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class VirtualView {

    private final HashMap<String, ObjectOutputStream> outputMap;

    /**
     * Creates an hashMap where the keys are the usernames and the values
     * are the corresponding client's ObjectOutputStream
     * @param hashMap hash table <username,socket>
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public VirtualView(HashMap<String,ObjectOutputStream> hashMap) throws IOException {
        outputMap = hashMap;
    }

    /**
     * Sends an updated mapVM to the clients
     * @param mapVM mapVM sent to the client
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void updateMap(MapVM mapVM) throws IOException {
        for(ObjectOutputStream output : outputMap.values()) {
            output.writeObject(mapVM);
        }
    }

    /**
     * Sends an updated builderVM to the clients
     * @param builderVM builderVM sent to the client
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void updateBuilders(BuilderVM builderVM) throws IOException {
        for(ObjectOutputStream output : outputMap.values()) {
            output.writeObject(builderVM);
        }
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a move input request
     * @param player player username
     * @param checkMoveCells list of legal coords that can be selected by the client for the move
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void moveInput(String player, List<Coords> checkMoveCells, boolean error) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(0);
        message.setCoordsList(checkMoveCells);
        message.setError(error);
        outputMap.get(player).writeObject(message);
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a build input request
     * @param player player username
     * @param checkBuildCells list of legal coords that can be selected by the client for the build
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void buildInput(String player, List<Coords> checkBuildCells, boolean error) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(1);
        message.setCoordsList(checkBuildCells);
        message.setError(error);
        outputMap.get(player).writeObject(message);
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a god input request
     * @param player player username
     * @param chosenGods list of gods chosen by the challenger among which the player can choose his god
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void godInput(String player, List<String> chosenGods, boolean error) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(3);
        message.setStringList(chosenGods);
        message.setError(error);
        outputMap.get(player).writeObject(message);
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
        MessageCV message = new MessageCV();
        message.setId(4);
        message.setCallNumber(callNumber);
        message.setError(error);
        outputMap.get(player).writeObject(message);
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a god selection input request
     * @param challenger challenger player username
     * @param godsList list of all gods
     * @param godsNumber number of gods he has to choose
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void godSelectionInput(String challenger, List<String> godsList, int godsNumber, boolean error) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(5);
        message.setStringList(godsList);
        message.setGodsNumber(godsNumber);
        message.setError(error);
        outputMap.get(challenger).writeObject(message);
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a "useEffect" request
     * @param player player username
     * @param god name of the player's god
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void effectInput(String player, String god) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(6);
        message.setString(god);
        outputMap.get(player).writeObject(message);
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a choose builder request
     * @param player player username
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void chooseBuilder(String player) throws IOException {
        MessageCV message = new MessageCV();
        message.setString(player);
        message.setId(7);
        outputMap.get(player).writeObject(message);
    }

    /**
     * Sends to the player's client a MessageCV
     * representing a remove block request
     * @param player player username
     * @param removableBlocks list of legal coords that can be selected by the client for the removal
     * @param error notifies the client that this message is sent due to a previous input error
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void removeBlock(String player, List<Coords> removableBlocks, boolean error) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(8);
        message.setCoordsList(removableBlocks);
        message.setError(error);
        outputMap.get(player).writeObject(message);
    }

    public void notifyWin(String username) throws IOException {
        MessageCV message = new MessageCV();
        message.setId(9);
        outputMap.get(username).writeObject(message);
    }
}
