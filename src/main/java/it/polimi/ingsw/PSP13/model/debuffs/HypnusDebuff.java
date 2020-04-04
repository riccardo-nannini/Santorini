package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

public class HypnusDebuff extends Decorator {

    public HypnusDebuff(Turn god) {
        super(god);
    }

    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        if (this.isHigher(builder)) {
            return false;
        }
        return super.checkMove(builder, coords);
    }

    private boolean isHigher(Builder builder) {
        Player player = match.getPlayerByBuilder(builder);
        Builder otherBuilder = player.getBuilders()[0] == builder ? player.getBuilders()[1] : player.getBuilders()[0];
        return builder.getHeight() > otherBuilder.getHeight();
    }
}
