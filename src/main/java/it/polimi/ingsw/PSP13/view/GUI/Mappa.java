package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;
import it.polimi.ingsw.PSP13.view.CLI.BuilderColor;
import it.polimi.ingsw.PSP13.view.CLI.MapPrinter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mappa {


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

    private GuiInput guiInput;

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
    public void initialize() {

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



    @FXML
    public void builderSetUpInput() {
        turnPhase = 0;
        textInfo.setText("Click on a cell to place your workers");
        //fare in modo che le celle già occupate da altri giocatori non siano cliccabili
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


    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
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

    int turnPhase;

    @FXML
    public void clickPane(MouseEvent e) {
        if (turnPhase == 0) {
            Node source = (Node) e.getSource();
            Integer colIndex = (GridPane.getColumnIndex(source) != null ? GridPane.getColumnIndex(source) : 0);
            Integer rowIndex = (GridPane.getRowIndex(source) != null ? GridPane.getRowIndex(source) : 0);
            Coords clickedCoords = new Coords(rowIndex,colIndex);
            guiInput.getController().notifySetupBuilder(clickedCoords);
        }


    }



    @FXML
    public void released(MouseEvent e) {

    }

    @FXML
    public void initializeMap() {

    }






}
