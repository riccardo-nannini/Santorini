package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.view.CLI.MapPrinter;

public class BuilderObserver {

    private MapPrinter mapPrinter;


    public BuilderObserver(MapPrinter mapPrinter) {
        this.mapPrinter = mapPrinter;
    }

    public void updateMapPrinter(BuilderVM builderVM) {
        mapPrinter.updateBuildersCLI(builderVM);
    };
}
