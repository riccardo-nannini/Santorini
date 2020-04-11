package it.polimi.ingsw.PSP13.view.Immutables;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;


import java.util.HashMap;
import java.util.Map;

public class BuilderView {

    private final Map<Color, Coords[]> map;

    public Coords[] getCoords(Color color){
        return map.get(color).clone();
    }

    /**
     * creates an immutable builder
     */
    public BuilderView()
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
     * @param color the color that identifies the builders
     */
    public void updateBuilder(Coords[] builders, Color color)
    {
        map.put(color,builders);
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

        for(Map.Entry entry : map.entrySet())
        {
            Coords[] coords = ((Coords[])entry.getValue());
            if(coords != null)
            {
                if(coords[0].equals(par) || coords[1].equals(par))
                    return (Color)entry.getKey();
            }
        }

        return null;
    }




}
