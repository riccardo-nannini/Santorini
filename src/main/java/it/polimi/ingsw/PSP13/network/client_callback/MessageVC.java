package it.polimi.ingsw.PSP13.network.client_callback;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.Serializable;
import java.util.List;

public class MessageVC implements Serializable {

    private static final long serialVersionUID = 87L;

    private final int id;

    private final String string;
    private final Coords coords;

    public MessageVC(int id, String string, Coords coords)
    {
        this.id = id;
        this.string = string;
        this.coords = coords;
    }

    public int getId() {
        return id;
    }

    public String getString() {
        return string;
    }

    public Coords getCoords() {
        return coords;
    }


}
