package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GodSelection implements GodHandlerInterface {

    private final GodDispatcherGUI godDispatcher;
    private List<String> godsList;
    private List<ImageView> icons = new ArrayList<>();
    private int godsNumber;
    private List<String> selectedGods = new ArrayList<>();
    private boolean isChallenger;

    public GodSelection(GodDispatcherGUI godDispatcher) {
        this.godDispatcher = godDispatcher;
    }

    @Override
    public void initialization() {
        icons.add(godDispatcher.godIcon1);
        icons.add(godDispatcher.godIcon2);
        icons.add(godDispatcher.godIcon3);
        icons.add(godDispatcher.godIcon5);
        icons.add(godDispatcher.godIcon6);
        icons.add(godDispatcher.godIcon4);
        icons.add(godDispatcher.godIcon7);
        icons.add(godDispatcher.godIcon8);
        icons.add(godDispatcher.godIcon9);
        icons.add(godDispatcher.godIcon10);
        icons.add(godDispatcher.godIcon11);
        icons.add(godDispatcher.godIcon12);
        icons.add(godDispatcher.godIcon13);
        icons.add(godDispatcher.godIcon14);
        icons.add(godDispatcher.godIcon15);
        icons.add(godDispatcher.godIcon16);
    }

    @Override
    public void upload() {
        initialization();
        godDispatcher.descriptionBox.setEditable(false);
        godDispatcher.infoBox.setDisable(true);
        Text text = new Text();
        if (isChallenger) {
            text.setText("   Select " + godsNumber +" gods");
        } else {
            text.setText("  Wait while the challenger is chosing the gods..");
        }
        godDispatcher.infoBox.getChildren().add(text);
        for (int godsIndex=0; godsIndex < godsList.size(); godsIndex++) {
            ImageView img = icons.get(godsIndex);
            img.setImage(new Image("Icons/" + godsList.get(godsIndex) +".png"));
            img.setId(godsList.get(godsIndex));
        }
    }

    @Override
    public void godClicked(Event event) {
        String id = ((MouseEvent) event).getPickResult().getIntersectedNode().getId();

        if (((MouseEvent) event).getButton() == MouseButton.PRIMARY && isChallenger) {
            Node selectedImage = ((MouseEvent) event).getPickResult().getIntersectedNode();
            if (selectedImage.getBlendMode() != null && selectedImage.getBlendMode().equals(BlendMode.MULTIPLY)) {
                selectedGods.remove(id);
                selectedImage.setBlendMode(BlendMode.SRC_OVER);
            } else {
                selectedGods.add(id);
                selectedImage.setBlendMode(BlendMode.MULTIPLY);
            }

            System.out.println("Click " + id);
        }
        if (((MouseEvent) event).getButton() == MouseButton.SECONDARY) {
            Image cardImage = new Image("Cards/" + id + ".png");
            godDispatcher.card.setImage(cardImage);
            System.out.println("Click dx");
        }
    }

    @Override
    public void confirmClicked() {
        if (!isChallenger) return;
        if (selectedGods.size() != godsNumber) {
            if (godDispatcher.infoBox.getChildren().size() < 2) {
                Text text = new Text();
                text.setStyle("-fx-fill: RED;");
                text.setText("\nPlease select the right number of gods");
                godDispatcher.infoBox.getChildren().add(text);
            }
        } else {
            godDispatcher.getGuiInput().getController().notifyGodSelection(List2String(selectedGods));
            System.out.println("Confermato");
        }
    }

    private String List2String(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int element = 0; element < list.size(); element++) {
            result.append(list.get(element));
            if (element < list.size() - 1) result.append(" ,");
        }
        return result.toString();
    }

    public void helperClicked() {
        System.out.println("Helper clicked");
    }

    public void setGodsList(List<String> godsList) {
        this.godsList = godsList;
    }

    public void setGodsNumber(int godsNumber) {
        this.godsNumber = godsNumber;
    }

    public void setIsChallenger (boolean isChallenger) {
        this.isChallenger = isChallenger;
    }
}
