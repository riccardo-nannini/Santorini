package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;
import javafx.application.Platform;

import java.util.List;

public class GuiInput extends Input {

    private Lobby loginController;
    private GodSelectionGUI godSelection = null;
    private Mappa mappaController;


    public void setMapController(Mappa mapController) {
        this.mappaController = mapController;
    }

    @Override
    public void setEffectDescription(String effect) {
        Platform.runLater(() -> {
            try {
                mappaController.setClientEffectDescription(effect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateBuilders(BuilderVM builderVM){
        Platform.runLater(() -> {
            try {
                mappaController.updateBuiders(builderVM.getColor(), builderVM.getBuilders());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateMap(MapVM mapVM) {
        Platform.runLater(() -> {
            try {
                mappaController.updateMap(mapVM);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setupClientsInfo(MessageClientsInfoCV messageClientsInfoCV) {
        Platform.runLater(() -> {
            try {
                mappaController.setUpClientsInfo(messageClientsInfoCV);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public void moveInput(List<Coords> checkMoveCells, boolean error) {

    }

    @Override
    public void buildInput(List<Coords> checkBuildCells, boolean error) {

    }

    @Override
    public void nicknameInput(boolean error) {
        Platform.runLater(() -> {
            System.out.println("sono dentro");
            if(error)
            {
                System.out.println("errore");
                loginController.nicknameError();
            }


        });
    }

    @Override
    public void godInput(List<String> chosenGods, boolean error) {

    }

    @Override
    public void builderSetUpInput(boolean callNumber, boolean error) {
        Platform.runLater(() -> {
            try {
                mappaController.builderSetUpInput();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void godSelectionInput(List<String> godsList, int godsNumber, boolean error) {
        Platform.runLater(()->{ //TODO se error == true non devo cambiare scena ma solo aggiornare quella già esistente attraverso attributo godSelection
            try {
                loginController.sceneChangeGodSelection(godsList, godsNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void effectInput(String god) {

    }

    @Override
    public void chooseBuilder(String player) {

    }

    @Override
    public void removeBlock(List<Coords> removableBlocks, boolean error) {

    }

    @Override
    public void disconnectionMessage() {

    }

    @Override
    public void choosePlayerNum(boolean error) {
        synchronized (this)
        {
            System.out.println("scegli il numero di giocatori");
            notifyAll();

            if(loginController.isNicknameSent())
            {
                Platform.runLater(()->{
                    try {
                        loginController.chooseP();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        }
    }

    @Override
    public void printWaitGodsSelection(String challenger) {

        /*Platform.runLater(()->{
            try {
                loginController.sceneChange();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/

    }

    @Override
    public void printWaitGodSelection(String player) {

    }

    @Override
    public void playAgain() {

    }

    @Override
    public void lobbyWait() {

    }

    public void setLoginController(Lobby loginController) {
        this.loginController = loginController;
    }

    public void setGodSelection(GodSelectionGUI godSelection) {
        this.godSelection = godSelection;
    }

    public ObservableToController getController()
    {
        return controller;
    }
}
