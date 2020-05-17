package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.model.player.Coords;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;
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

    /*
    //GET COORDINATES FROM A MOUSE CLICK ON THE MAP
    @FXML
    public void clickPane(MouseEvent e) {
        Node source = (Node)e.getSource() ;
        Integer colIndex = ( GridPane.getColumnIndex(source) != null ? GridPane.getColumnIndex(source) : 0);
        Integer rowIndex = ( GridPane.getRowIndex(source) != null ? GridPane.getRowIndex(source) : 0);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
        ImageView v1 = new ImageView("resources/podium-characters-Jason.png");
        v1.setFitHeight(75);
        v1.setFitWidth(75);
        grid.add(v1,colIndex,rowIndex);
    }

     */

    String imageShowed = "";
    String god1 = "APOLLO";
    String effect1 ="Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated";
    String god2 = "ARES";
    String effect2 ="You may remove an unoccupied block (not dome) neighboring your unmoved Worker";
    String god3 = "ARTEMIS";
    String effect3 ="Your Worker may move one additional time, but not back to the space it started on";


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
                    godName.setText(god1);
                    textEffect.setText(effect1);
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
    public void clickPane(MouseEvent e) {
        Node source = (Node) e.getSource();
        Integer colIndex = (GridPane.getColumnIndex(source) != null ? GridPane.getColumnIndex(source) : 0);
        Integer rowIndex = (GridPane.getRowIndex(source) != null ? GridPane.getRowIndex(source) : 0);

        List<Coords> adjacents = new ArrayList();
        int x = colIndex - 1;
        int y = rowIndex - 1;
        Coords clickedCoords = new Coords(colIndex, rowIndex);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Coords temp = new Coords(x + i, y + j);
                adjacents.add(temp);
            }
        }
        adjacents.remove(clickedCoords);
        for (Node pane : grid.getChildren()) {
            Integer xx = (GridPane.getColumnIndex(pane) != null ? GridPane.getColumnIndex(pane) : 0);
            Integer yy = (GridPane.getRowIndex(pane) != null ? GridPane.getRowIndex(pane) : 0);
            Coords tempCoords = new Coords(xx, yy);
            if (adjacents.contains(tempCoords)) {
                pane.getStyleClass().add("highlighted");
            }
        }
    }
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



    }





}
