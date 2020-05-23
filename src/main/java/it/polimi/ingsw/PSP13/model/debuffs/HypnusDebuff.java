package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HypnusDebuff extends Decorator {

    private Player player;
    public HypnusDebuff(Turn god) {
        super(god);
    }

    @Override
    public void start(String player) throws IOException {
        god.start(player);
    }

    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        god.move(builder, coords);
    }

    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        if (this.isHigher(builder)) {
            return false;
        }
        return super.checkMove(builder, coords);
    }

    private boolean isHigher(Builder builder) {
        player = match.getPlayerByBuilder(builder);
        Builder otherBuilder = player.getBuilders()[0] == builder ? player.getBuilders()[1] : player.getBuilders()[0];
        return builder.getHeight() > otherBuilder.getHeight();
    }

    @Override
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        god.build(builder, buildingPosition);
    }

    @Override
    public boolean checkBuild(Builder builder, Coords buildingPosition) {
        return god.checkBuild(builder, buildingPosition);
    }

    @Override
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition) {
        return god.checkWin(builder,precedentPosition,currentPosition);
    }

    @Override
    public List<Coords> getCellMoves(Builder builder) {
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        List<Coords> possibleMove = new ArrayList<>();

        for(Coords coords : adjacents)
        {
            if(this.checkMove(builder, coords))
                possibleMove.add(coords);
        }
        return possibleMove;
    }

    @Override
    public List<Coords> getCellBuilds(Builder builder) {
        return god.getCellBuilds(builder);
    }

    /**
     * Removes the AthenaDebuff decorator since the effect only applies for one turn
     */
    @Override
    public void end() throws IOException {
        god.end();
        removeDecorator(player);
    }
}



