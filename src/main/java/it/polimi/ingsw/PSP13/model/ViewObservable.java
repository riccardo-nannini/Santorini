package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;


public class ViewObservable {

    private Match match;
    private VirtualView virtualView;

    public ViewObservable (Match match, VirtualView virtualView) throws IOException {
        this.match = match;
        this.virtualView = virtualView;
        notifyMap();
    }

    /**
     * Notifies view's MapObserver sending a MapVM object
     */
    public void notifyMap() throws IOException {
        virtualView.updateMap(new MapVM(match.getMap().getMatrix()));
    };

    /**
     * Notifies view's BuilderObserver sending a BuilderVM object
     * @param builder1 builder used to construct BuilderVM
     * @param builder2 builder used to construct BuilderVM
     */
    public void notifyBuilder(Builder builder1, Builder builder2) throws IOException {
        Coords[] builders = new Coords[2];
        builders[0] = builder1.getCoords();
        builders[1] = builder2.getCoords();
        virtualView.updateBuilders(new BuilderVM(builders, match.getPlayerByBuilder(builder1).getColor()));
    };



}
