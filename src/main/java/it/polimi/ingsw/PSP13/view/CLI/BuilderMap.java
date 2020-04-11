package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.Immutables.WorkerVM;


import java.util.HashMap;
import java.util.Map;

public class BuilderMap {

    private final Map<Color, WorkerVM> map;

    public Coords[] getCoords(Color color){
        return map.get(color).getBuilders();
    }

    /**
     * creates an immutable builder
     */
    public BuilderMap()
    {
        map = new HashMap<>();
        map.put(Color.Blue,null);
        map.put(Color.Red,null);
        map.put(Color.Violet,null);
        map.put(Color.White,null);
        map.put(Color.Yellow,null);

    }

    /**
     * updates the position of the builders identified by color
     * @param builders the new values
     */
    public void updateBuilder(WorkerVM builders)
    {
        map.put(builders.getColor(),builders);
    }

    /**
     * check if there is a builder on coordinates (x,y)
     * @param x
     * @param y
     * @return the color of the builder that lies on coordinates (x,y), null if there is no builder
     */
    public Color checkBuilder(int x, int y)
    {
        Coords par = new Coords(x,y);
        Coords[] coords = null;

        for(Map.Entry entry : map.entrySet())
        {
            if(entry.getValue() != null)
                coords = ((WorkerVM)entry.getValue()).getBuilders();
            if(coords != null)
            {
                if(coords[0].equals(par) || coords[1].equals(par))
                    return (Color)entry.getKey();
            }
        }

        return null;
    }




}
