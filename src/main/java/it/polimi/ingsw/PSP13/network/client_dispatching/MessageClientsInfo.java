package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.model.player.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageClientsInfo implements Serializable {

    private static final long serialVersionUID = 424L;

    private String clientUsername;
    private Color clientColor;
    private String clientGod;
    private String clientEffect;
    private List<String> opponentsUsernames;
    private List<Color> opponentsColors;
    private List<String> opponentsGod;
    private List<String> opponentsEffects;

    public MessageClientsInfo() {
        opponentsUsernames = new ArrayList<>();
        opponentsColors = new ArrayList<>();
        opponentsGod = new ArrayList<>();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientGod() {
        return clientGod;
    }

    public void setClientGod(String clientGod) {
        this.clientGod = clientGod;
    }

    public List<String> getOpponentsUsernames() {
        return opponentsUsernames;
    }

    public void setOpponentsUsernames(List<String> opponentsUsernames) {
        this.opponentsUsernames = opponentsUsernames;
    }

    public List<Color> getOpponentsColors() {
        return opponentsColors;
    }

    public void setOpponentsColors(List<Color> opponentsColors) {
        this.opponentsColors = opponentsColors;
    }

    public List<String> getOpponentsGod() {
        return opponentsGod;
    }

    public void setOpponentsGod(List<String> opponentsGod) {
        this.opponentsGod = opponentsGod;
    }

    public Color getClientColor() {
        return clientColor;
    }

    public void setClientColor(Color clientColor) {
        this.clientColor = clientColor;
    }

    public List<String> getOpponentsEffects() {
        return opponentsEffects;
    }

    public void setOpponentsEffects(List<String> opponentsEffects) {
        this.opponentsEffects = opponentsEffects;
    }

    public String getClientEffect() {
        return clientEffect;
    }

    public void setClientEffect(String clientEffect) {
        this.clientEffect = clientEffect;
    }
}
