package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.view.CLI.MapPrinter;

public class MapObserver {

    private MapPrinter mapPrinter;


    public MapObserver(MapPrinter mapPrinter) {
        this.mapPrinter = mapPrinter;
    }

    public void updateMapPrinter(MapVM mapVM) {
        mapPrinter.updateMapCLI(mapVM);
    };


}
