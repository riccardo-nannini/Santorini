package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;
import it.polimi.ingsw.PSP13.view.GUI.status.*;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GuiInput extends Input {

    private Lobby loginController;
    private GodDispatcher godDispatcher = null;
    private GameMap map;
    private boolean mapInitialization = true;
    private boolean isWinner;


    @Override
    public void setEffectDescription(String effect, List<String> godsEffect) {
        Platform.runLater(() -> {
            try {
                if (godDispatcher != null) godDispatcher.setGodEffects(godsEffect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateBuilders(BuilderVM builderVM){
        Platform.runLater(() -> {
            try {
                map.updateBuilders(builderVM.getColor(), builderVM.getBuilders());
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
    public void setup() {}

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
                    loginController.goBacktoNicknameSceneChange();
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
    public void printWaitStarterSelection(String challenger) {}

    @Override
    public void lobbyWait() {}

    @Override
    public void waitQueueMsg() {
        Platform.runLater(() -> {
            loginController.FullLobbyWaitMsg();
        });
    }

    @Override
    public void effectInput(String god) {

        Platform.runLater(()->{
            try {
                map.askEffect(god);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            map.removeBlock(removableBlocks);
        });
    }

    private void OpponentDisconnection() throws IOException {
        Stage discPopUp = new Stage();

        Font.loadFont( GodDispatcher.class.getResource("/fonts/RobotoCondensed-Regular.ttf").toExternalForm(), 18);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiInput.class.getResource("/disconnection.fxml"));
        AnchorPane disconnectionPane = loader.<AnchorPane>load();

        Scene scene = new Scene(disconnectionPane);
        scene.getStylesheets().add("god_selection.css");
        discPopUp.initModality(Modality.APPLICATION_MODAL);
        discPopUp.setTitle("Disconnection");
        discPopUp.setScene(scene);

        discPopUp.showAndWait();
    }

    @Override
    public void disconnectionMessage() {

        Platform.runLater(()->{
            try {
                OpponentDisconnection();
                if (mapInitialization) {
                    godDispatcher.setSceneLogin();
                } else {
                    map.setSceneLogin();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void choosePlayerNum(boolean error) {
        System.out.println("rpova");
        synchronized (this)
        {
            System.out.println("scegli il numero di giocatori");

            /*Platform.runLater(()->{
                Event.fireEvent(loginController.getSlide1(),new UpdateEvent(Javafx.etype));
            });*/

            if(loginController.isNicknameSent())
            {
                System.out.println("rematch");
                Platform.runLater(()->{
                    try {
                        loginController.choosePlayerNum();
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
    public void printAssignedGod(String assignedGod) {}

    @Override
    public void notifyWin() {
        isWinner = true;
    }

    @Override
    public void notifyLose() {
        isWinner = false;
    }

    @Override
    public void playAgain() {

        Platform.runLater(()->{
            try {
                map.endgameSceneChange();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mapInitialization = true;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setLoginController(Lobby loginController) {
        this.loginController = loginController;
    }

    public void setGodDispatcher(GodDispatcher godDispatcher) {
        this.godDispatcher = godDispatcher;
    }

    public ObservableToController getController()
    {
        return controller;
    }

    public GodDispatcher getGodDispatcher() { return godDispatcher; }

    public boolean isWinner() {
        return isWinner;
    }

    @Override
    public void turnEnded() {
        Platform.runLater(()->{
            map.turnEnded();
        });
    }

}
