package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class GodSelectionGUI {

    private List<String> godsList;
    private int godsNumber;
    private List<String> selectedGods = new ArrayList<>();

    private GuiInput guiInput;

    public AnchorPane anchorPane1;
    public AnchorPane anchorPane2;
    public AnchorPane anchorPane3;
    public AnchorPane anchorPane4;
    public ImageView card;
    public ImageView confirm;
    public TextFlow infoBox;
    public TextArea descriptionBox;

    public void upload() {
        descriptionBox.setEditable(false);
        infoBox.setDisable(true);
        Text text = new Text();
        text.setText("  Select " + godsNumber +" gods");
        infoBox.getChildren().add(text);
        for (int godsIndex=0; godsIndex < godsList.size(); godsIndex++) {
            ImageView img = new ImageView();
            img.setImage(new Image("Icons/" + godsList.get(godsIndex) +".png"));
            img.setFitWidth(136);
            img.setFitHeight(136);
            img.setLayoutX((godsIndex%4) * 200);
            img.setPreserveRatio(true);
            img.setId(godsList.get(godsIndex));
            img.setOnMouseClicked(this::godClicked);
            img.setCursor(Cursor.HAND);
            if (godsIndex <= 3) {
                anchorPane1.getChildren().add(img);
            } else if (godsIndex <= 7) {
                anchorPane2.getChildren().add(img);
            } else if (godsIndex <= 11) {
                anchorPane3.getChildren().add(img);
            } else {
                anchorPane4.getChildren().add(img);
            }
        }
    }

    public void godClicked(Event event) {
        String id = ((MouseEvent) event).getPickResult().getIntersectedNode().getId();

        if (((MouseEvent) event).getButton() == MouseButton.PRIMARY) {
            Node img = ((MouseEvent) event).getPickResult().getIntersectedNode();
            if (img.getBlendMode() != null && img.getBlendMode().equals(BlendMode.MULTIPLY)) {
                selectedGods.remove(id);
                img.setBlendMode(BlendMode.SRC_OVER);
            } else {
                selectedGods.add(id);
                img.setBlendMode(BlendMode.MULTIPLY);
            }

            System.out.println("Click " + id);
        }
        if (((MouseEvent) event).getButton() == MouseButton.SECONDARY) {
            Image cardImage = new Image("Cards/" + id + ".png");
            card.setImage(cardImage);
            System.out.println("Click dx");
        }
    }

    public void confirmClicked() {
        if (selectedGods.size() != godsNumber) {
            if (infoBox.getChildren().size() < 2) {
                Text text = new Text();
                text.setStyle("-fx-fill: RED;");
                text.setText("\nPlease select the right number of gods");
                infoBox.getChildren().add(text);
            }
        } else {
            //guiInput.getController().notifyGodSelection(List2String(selectedGods));
            System.out.println("Confermato");
        }
    }

    public void helperClicked() {
        System.out.println("Helper clicked");
    }

    private String List2String(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int element = 0; element < list.size(); element++) {
            result.append(list.get(element));
            if (element < list.size() - 1) result.append(" ,");
        }
        return result.toString();
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }

    public void setGodsList(List<String> godsList) {
        this.godsList = godsList;
    }

    public void setGodsNumber(int godsNumber) {
        this.godsNumber = godsNumber;
    }
}
