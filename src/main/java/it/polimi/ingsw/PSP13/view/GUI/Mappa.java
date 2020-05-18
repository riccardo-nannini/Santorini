package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Mappa implements Initializable {


    private GuiInput guiInput;
    private TurnStatus status;


    @FXML
    private TextArea textInfo1;

    @FXML
    private TextArea textInfo2;

    @FXML
    private TextArea textInfo3;

    @FXML
    private ImageView imageInfo1;

    @FXML
    private ImageView imageInfo2;

    @FXML
    private ImageView imageInfo3;


    @FXML
    private GridPane grid;

    @FXML
    private TextArea textEffect;

    @FXML
    private TextArea textInfo;

    @FXML
    private TextArea godName;

    private String clientGodName;
    private String clientGodEffect;
    private String god2;
    private String effect2;
    private String god3;
    private String effect3;

    HashMap<Color, ArrayList<ImageView>> buildersImages = new HashMap<>();

    private final String level1 = "resources/Buildings/Level1.png";
    private final String level2 = "resources/Buildings/Level2.png";
    private final String level3 = "resources/Buildings/Level3.png";
    private final String level1Dome = "resources/Buildings/Level1_Dome.png";
    private final String level2Dome = "resources/Buildings/Level2_Dome.png";
    private final String level3Dome = "resources/Buildings/Level3_Dome.png";
    private final String dome = "resources/Buildings/Dome.png";



    @FXML
    public void selectCell(MouseEvent e) {
        Node source = (Node)e.getSource() ;
        int colIndex = ( GridPane.getColumnIndex(source) != null ? GridPane.getColumnIndex(source) : 0);
        int rowIndex = ( GridPane.getRowIndex(source) != null ? GridPane.getRowIndex(source) : 0);

        status.selectCell(new Coords(colIndex,rowIndex));
        grid.setDisable(true);
    }


    //TODO sistemare invio dell'effetto dei Dei mettendo tutti gli effetti in MessageCLientsInfoCV
    //TODO e sistemare quindi l'assegnazione di effect2 e effect3 e godEffectClient
    public void setUpClientsInfo(MessageClientsInfoCV clientsInfo) {

        textInfo1.setText(clientsInfo.getClientUsername());
        clientGodName = clientsInfo.getClientGod();
        File file1 = new File("resources/Icons/"+clientGodName+".png");
        Image image1 = new Image(file1.toURI().toString());
        imageInfo1.setImage(image1);


        god2 = clientsInfo.getOpponentsGod().get(0);
        File file2 = new File("resources/Icons/"+god2+".png");
        Image image2 = new Image(file2.toURI().toString());
        imageInfo2.setImage(image2);
        textInfo2.setText(clientsInfo.getOpponentsUsernames().get(0));
        setBuildersImages(clientsInfo.getOpponentsColors().get(0),clientsInfo.getOpponentsUsernames().get(0));
        effect2 = "";


        if (clientsInfo.getOpponentsUsernames().size() == 2) {
            god3 = clientsInfo.getOpponentsGod().get(1);
            File file3 = new File("resources/Icons/"+god3+".png");
            Image image3 = new Image(file3.toURI().toString());
            imageInfo3.setImage(image3);
            textInfo3.setText(clientsInfo.getOpponentsUsernames().get(1));
            setBuildersImages(clientsInfo.getOpponentsColors().get(1),clientsInfo.getOpponentsUsernames().get(1));
            effect3 = "";

        }
    }


    public void setClientEffectDescription(String effect) {
        this.clientGodEffect = effect;
    }


    public void setBuildersImages(Color color, String god) {
        ImageView i1 = new ImageView("resources/Icons/"+god+".png");
        ImageView i2 = new ImageView("resources/Icons/"+god+".png");
        i1.setFitHeight(75);
        i1.setFitWidth(75);
        i2.setFitHeight(75);
        i2.setFitWidth(75);
        ArrayList<ImageView> i = new ArrayList<>();
        i.add(i1);
        i.add(i2);
        buildersImages.put(color,i);
    }


    @FXML
    public void updateBuiders(Color color, Coords[] coords) {
        grid.getChildren().remove(buildersImages.get(color).get(0));
        grid.getChildren().remove(buildersImages.get(color).get(1));
        grid.add(buildersImages.get(color).get(0),coords[0].getX(),coords[0].getY());
        grid.add(buildersImages.get(color).get(1),coords[1].getX(),coords[1].getY());
    }

    String imageShowed = "";
    @FXML
    public void showEffect(MouseEvent e) {
        Node source = (Node) e.getSource();
        String id = source.getId();
        if (imageShowed.equals(id)) {
            textInfo.setVisible(true);
            textEffect.setVisible(false);
            godName.setVisible(false);
        } else {
            switch (id) {
                case "imageInfo1":
                    godName.setText(clientGodName);
                    textEffect.setText(clientGodEffect);
                    break;
                case "imageInfo2":
                    godName.setText(god2);
                    textEffect.setText(effect2);
                    break;
                case "imageInfo3":
                    godName.setText(god3);
                    textEffect.setText(effect3);
                    break;
            }
                imageShowed = id;
                textInfo.setVisible(false);
                godName.setVisible(true);
                textEffect.setVisible(true);
        }
    }


    //le pedine che non sono pane possono creare problemi???
    //essendo loro figli di grid
    //grid.removeAll() pulisce la griglia dagli edifici precedenti, ma le pedine che fine fanno
    //soluzione ricaricare tutti i builders ma servirebbe salvarsi le posizioni
    public void updateMap(MapVM mapVM) {
        grid.getChildren().removeAll();
        for (Node pane : grid.getChildren()) {
            Integer x = (GridPane.getColumnIndex(pane) != null ? GridPane.getColumnIndex(pane) : 0);
            Integer y = (GridPane.getRowIndex(pane) != null ? GridPane.getRowIndex(pane) : 0);
            String buildingUrl = "";
            int cellHeight = mapVM.getMap()[x][y].getLevel().getHeight();
            boolean isDome = mapVM.getMap()[x][y].getDome();
            switch (cellHeight) {
                case 0:
                    buildingUrl = isDome ? dome : "clear";
                    break;
                case 1:
                    buildingUrl = isDome ? level1Dome : level1;
                    break;
                case 2:
                    buildingUrl = isDome ? level2Dome : level2;
                    break;
                case 3:
                    buildingUrl = isDome ? level3Dome: level3;
                    break;
            }
            if (!buildingUrl.equals("clear")) {
                ImageView building = new ImageView(buildingUrl);
                building.setFitHeight(100);
                building.setFitWidth(100);
                grid.add(building,x,y);
            }
            if (mapVM.getMap()[x][y].getDome()) {
                ImageView domeImage = new ImageView(dome);
                //check dimensioni
                domeImage.setFitWidth(50);
                domeImage.setFitHeight(50);
                grid.add(domeImage,x,y);
            }
        }
    }

    public void setPaneClickable(ArrayList<Coords> coordsList) {
        for (Node pane : grid.getChildren()) {
            Integer x = (GridPane.getColumnIndex(pane) != null ? GridPane.getColumnIndex(pane) : 0);
            Integer y = (GridPane.getRowIndex(pane) != null ? GridPane.getRowIndex(pane) : 0);
            Coords gridCoords = new Coords(x, y);
            if (coordsList.contains(gridCoords)) {
                pane.setMouseTransparent(false);
            } else {
                pane.setMouseTransparent(true);
            }
        }
    }


    //TODO sistemare
    @FXML
    public void released(MouseEvent e) {
        for (Node pane : grid.getChildren()) {
            pane.getStyleClass().clear();
        }
    }

    @FXML
    public void initializeMap(MouseEvent e) {
        textInfo1.setText("Tony");
        textInfo2.setText("Nanno");
        textInfo3.setText("Simone");

        File file1 = new File("resources/podium-characters-Jason.png");
        Image image1 = new Image(file1.toURI().toString());
        imageInfo1.setImage(image1);

        File file2 = new File("resources/podium-characters-Minotaur.png");
        Image image2 = new Image(file2.toURI().toString());
        imageInfo2.setImage(image2);

        File file3 = new File("resources/podium-characters-Morpheus.png");
        Image image3 = new Image(file3.toURI().toString());
        imageInfo3.setImage(image3);

        grid.setDisable(false);

    }


    public void OpponentDisconnection() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "A player disconnected from the game, you will be put in the lobby again", ButtonType.OK);
        alert.showAndWait();
    }

    public void backToLobbySceneChange() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/lobby.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene lobby = new Scene(pane);
        lobby.getStylesheets().add("lobby.css");

        Stage stage = (Stage) (grid.getScene().getWindow());
        stage.setScene(lobby);

        Lobby lobby1 = loader.<Lobby>getController();
        lobby1.setGuiInput(guiInput);
        guiInput.setLoginController(lobby1);
    }

    public boolean askPlayAgain() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to play again?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            TurnStatus.map.getGuiInput().getController().notifyPlayAgain("yes");
            return true;
        }
        else {
            TurnStatus.map.getGuiInput().getController().notifyPlayAgain("no");
        }

        return false;

    }

    public void askEffect(String god) {
        textInfo.setText("Do you want to use the effect of " + god + " ?");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to use the effect of " + god + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            TurnStatus.map.getGuiInput().getController().notifyEffect("yes");
            textInfo.setText("you selected yes");
        }
        else {
            TurnStatus.map.getGuiInput().getController().notifyEffect("no");
            textInfo.setText("you selected no");
        }

    }


    public void removeBlock(List<Coords> removableBlocks, boolean error) {
        if(error)
            textInfo.setText("There was an error with your last selection, please try again.");
        else
            textInfo.setText("You can remove a block only from the highlighted cells.");

        highlightCells(removableBlocks);

        grid.setDisable(false);
    }

    private void highlightCells(List<Coords> checkMoveCells) {
        for (Node pane : grid.getChildren()) {
            int xx = (GridPane.getColumnIndex(pane) != null ? GridPane.getColumnIndex(pane) : 0);
            int yy = (GridPane.getRowIndex(pane) != null ? GridPane.getRowIndex(pane) : 0);
            Coords tempCoords = new Coords(xx, yy);
            if (checkMoveCells.contains(tempCoords)) {
                pane.getStyleClass().add("highlighted");
            }
        }
    }

    public void build(List<Coords> checkBuildCells, boolean error) {
        if(error)
            textInfo.setText("There was an error with your last selection, please try again.");
        else
            textInfo.setText("Now, you have to build a block. You can build on the highlighted cells.");

        highlightCells(checkBuildCells);

        grid.setDisable(false);
    }

    public void move(List<Coords> checkMoveCells, boolean error) {

        if(error)
            textInfo.setText("There was an error with your last selection, please try again.");
        else
            textInfo.setText("It's time to move your worker! You can move him only on the highlighted cells");

        highlightCells(checkMoveCells);

        grid.setDisable(false);
    }

    public void chooseBuilder() {

        textInfo.setText("It's your turn! Please select one of your builders");
        grid.setDisable(false);
    }

    //TODO fare in modo che le celle già occupate da altri giocatori non siano cliccabili
    public void builderSetup(boolean callnumber, boolean error){
        if(!callnumber)
            textInfo.setText("It's your turn! Please place your first builder");
        else
            textInfo.setText("Please, place your second builder");

        if(error)
            textInfo.setText("There was an error with your last selection, please try again.");

        grid.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TurnStatus.setMap(this);
    }



    public void setStatus(TurnStatus status) {
        this.status = status;
    }

    public GuiInput getGuiInput() {
        return guiInput;
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }


}
