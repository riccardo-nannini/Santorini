package it.polimi.ingsw.PSP13.model.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Color {

    White, Red, Yellow, Violet, Blue;

    public static List<Color> getColors() {
        List<Color> colors = new ArrayList<>();
        Collections.addAll(colors, values());
        return colors;
    }
}
