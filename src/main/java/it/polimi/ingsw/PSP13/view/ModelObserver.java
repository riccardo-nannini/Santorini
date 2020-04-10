package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.view.Immutables.MapView;
import it.polimi.ingsw.PSP13.view.Immutables.BuilderView;

import java.util.List;

public class ModelObserver {

    private MapView map;
    private List<BuilderView> builders;

    public MapView getMap() {
        return map;
    }

    public void setMap(MapView map) {
        this.map = map;
    }

    public List<BuilderView> getBuilders() {
        return builders;
    }

    public void setBuilders(List<BuilderView> builders) {
        this.builders = builders;
    }


}
