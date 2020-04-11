package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.view.BuilderObserver;
import it.polimi.ingsw.PSP13.view.CLI.MapPrinter;
import it.polimi.ingsw.PSP13.view.MapObserver;
import it.polimi.ingsw.PSP13.view.WinObserver;


public class ViewObservable {

    private MapObserver mapObserver;
    private BuilderObserver builderObserver;
    private WinObserver winObserver;
    private Match match;

    public ViewObservable (Match match) {
        MapPrinter mapPrinter = new MapPrinter();
        this.match = match;
        this.mapObserver = new MapObserver(mapPrinter);
        this.builderObserver = new BuilderObserver(mapPrinter);
        this.winObserver = new WinObserver(mapPrinter);
    }

    /**
     * Notifies view's MapObserver sending a MapVM object
     */
    public void notifyMap() {
        mapObserver.updateMapPrinter(new MapVM(match.getMap().getMatrix()));
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
        builderObserver.updateMapPrinter(new BuilderVM(builders, match.getPlayerByBuilder(builder1).getColor()));
    };



    public void notifyWin(Player player) {
        winObserver.notifyWin(player);
    };
}
