package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;
import it.polimi.ingsw.PSP13.view.GUI.status.*;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;

public class GuiInput extends Input {

    private Lobby loginController;
    private GodDispatcherGUI godDispatcher = null;
    private Mappa map;
    private boolean mapInitialization = true;


    @Override
    public void setEffectDescription(String effect, List<String> godsEffect) {
        Platform.runLater(() -> {
            try {
                if (godDispatcher != null) godDispatcher.setGodEffects(godsEffect);
                //map.setClientEffectDescription(effect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateBuilders(BuilderVM builderVM){
        Platform.runLater(() -> {
            try {
                map.updateBuiders(builderVM.getColor(), builderVM.getBuilders());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateMap(MapVM mapVM) {
        Platform.runLater(() -> {
            try {
                if (mapInitialization) {
                    godDispatcher.setSceneGameBoard();
                    mapInitialization = false;
                }
                map.updateMap(mapVM);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setupClientsInfo(MessageClientsInfoCV messageClientsInfoCV) {
        Platform.runLater(() -> {
            if (mapInitialization) {
                try {
                    godDispatcher.setSceneGameBoard();
                    mapInitialization = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                map.setUpClientsInfo(messageClientsInfoCV);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void moveInput(List<Coords> checkMoveCells, boolean error) {

        map.setStatus(new MoveStatus());
        Platform.runLater(()->{
            map.move(checkMoveCells);
        });

    }

    @Override
    public void buildInput(List<Coords> checkBuildCells, boolean error) {
        map.setStatus(new BuildStatus());
        Platform.runLater(()->{
            map.build(checkBuildCells);
        });
    }

    @Override
    public void nicknameInput(boolean error) {
        Platform.runLater(() -> {
            if(error)
            {
                if(first)
                    loginController.goBacktoNickname();
                loginController.nicknameError();
            }


        });
    }

    @Override
    public void godInput(List<String> chosenGods, boolean error) {
        Platform.runLater(()->{ //TODO se error == true non devo cambiare scena ma solo aggiornare quella già esistente attraverso attributo godSelection
            try {
                godDispatcher.setSceneGodInput(chosenGods, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void builderSetUpInput(boolean callNumber, boolean error) {


        Platform.runLater(()->{
            if (mapInitialization)  {
                try {
                    godDispatcher.setSceneGameBoard();
                    mapInitialization = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            map.setStatus(new SetupStatus());
            map.builderSetup(callNumber);
        });

    }

    @Override
    public void godSelectionInput(List<String> godsList, int godsNumber, boolean error) {
        Platform.runLater(()->{
            try {
                loginController.sceneChangeGodSelection(godsList, godsNumber, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void starterInput(boolean error, List<String> usernames) {
        Platform.runLater(() -> {
            try {
                godDispatcher.setSceneStarterSelection(usernames);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void effectInput(String god) {

        Platform.runLater(()->{
            map.askEffect(god);
        });
    }

    @Override
    public void chooseBuilder(String player) {
        map.setStatus(new SelectBuilderStatus());
        Platform.runLater(()->{
            map.chooseBuilder();
        });
    }

    @Override
    public void removeBlock(List<Coords> removableBlocks, boolean error) {
        map.setStatus(new RemoveBlockStatus());
        Platform.runLater(()->{
            map.removeBlock(removableBlocks, error);
        });
    }

    @Override
    public void disconnectionMessage() {

        Platform.runLater(()->{
            map.OpponentDisconnection();
            try {
                map.backToLobbySceneChange();
                loginController.rematch();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    @Override
    public void choosePlayerNum(boolean error) {
        synchronized (this)
        {
            System.out.println("scegli il numero di giocatori");

            /*Platform.runLater(()->{
                Event.fireEvent(loginController.getSlide1(),new UpdateEvent(Javafx.etype));
            });*/

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
    public void printWaitGodsSelection(String challenger, List<String> godsList) {

        Platform.runLater(()->{ //TODO se error == true non devo cambiare scena ma solo aggiornare quella già esistente attraverso attributo godSelection
            try {
                loginController.sceneChangeGodSelection(godsList,0, false );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void printWaitGodSelection(String player, List<String> chosenGods) {
        Platform.runLater(()->{
            try {
                godDispatcher.setSceneGodInput(chosenGods, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void playAgain() {

        Platform.runLater(()->{
            boolean result = map.askPlayAgain();
            try {
                map.backToLobbySceneChange();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result) {
                try {
                    loginController.rematch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setMap(Mappa map) {
        this.map = map;
    }

    public void setLoginController(Lobby loginController) {
        this.loginController = loginController;
    }

    public void setGodDispatcher(GodDispatcherGUI godDispatcher) {
        this.godDispatcher = godDispatcher;
    }

    public ObservableToController getController()
    {
        return controller;
    }

    public GodDispatcherGUI getGodDispatcher() { return godDispatcher; }
}
