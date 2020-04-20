package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.view.*;
import it.polimi.ingsw.PSP13.view.CLI.MapPrinter;


public class ViewObservable {


    private ModelObserver modelObserver;
    private Match match;

    public ViewObservable (Match match, Input input) {
        this.match = match;
        this.modelObserver = new ModelObserver(input);
        notifyMap();
    }

    /**
     * Notifies view's MapObserver sending a MapVM object
     */
    public void notifyMap() {
        modelObserver.updateMap(new MapVM(match.getMap().getMatrix()));
    };

    /**
     * Notifies view's BuilderObserver sending a BuilderVM object
     * @param builder1
     * @param builder2
     */
    public void notifyBuilder(Builder builder1, Builder builder2) {
        Coords[] builders = new Coords[2];
        builders[0] = builder1.getCoords();
        builders[1] = builder2.getCoords();
        modelObserver.updateBuilders(new BuilderVM(builders, match.getPlayerByBuilder(builder1).getColor()));
    };



}
