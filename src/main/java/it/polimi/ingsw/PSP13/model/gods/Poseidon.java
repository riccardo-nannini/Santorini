package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;

public class Poseidon extends Turn {

    private Builder unmovedBuilder;

    /**
     * Sets the effect description
     */
    public Poseidon() {
        this.unmovedBuilder = null;
        effect = "If your unmoved Worker is on the ground level, it may build up to three times in neighboring spaces";
    }


    /**
     * In addiction to turn's move, sets the unmoved builder
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        if (match.getPlayerByBuilder(builder).getBuilders()[0] == builder) {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[1];
        } else {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[0];
        }
        super.move(builder,coords);
    }

    /**
     * If the unmoved builder is on the ground level, it can build
     * up to three times in neighbouring cells
     */
    @Override
    public void end() throws IOException {
        if (match.getHeight(unmovedBuilder.getCoords()) == 0) {
            if (!getBuildableCells(unmovedBuilder).isEmpty()) {

                Coords buildCoords;
                int i = 0;
                String username = match.getPlayerByBuilder(unmovedBuilder).getUsername();
                while((i<3) && turnHandler.getInputUseEffect(username,"Poseidon") && !getBuildableCells(unmovedBuilder).isEmpty())
                {
                    buildCoords = turnHandler.getInputBuild(unmovedBuilder, getBuildableCells(unmovedBuilder));
                    super.build(unmovedBuilder, buildCoords);
                    i++;
                }
                match.notifyMap();


            }
        }
    }


}
