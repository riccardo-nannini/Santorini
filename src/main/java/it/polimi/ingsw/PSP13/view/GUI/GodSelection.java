package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
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
        godDispatcher.descriptionBox.setDisable(false);
        godDispatcher.infoBox.setDisable(true);
        godDispatcher.godName.setDisable(true);
        Text text = new Text();
        if (isChallenger) {
            text.setText("Select " + godsNumber +" gods");
        } else {
            text.setText("Wait while the challenger is chosing the gods..");
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
        }
    }

    @Override
    public void confirmClicked(Event event) {
        Node selectedImage = ((MouseEvent) event).getPickResult().getIntersectedNode();
        selectedImage.setBlendMode(BlendMode.SRC_OVER);
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

    public void helperClicked(Event event) throws IOException {
        List<String> message = new ArrayList<>();
        if (isChallenger) {
            message.add("Left click: selects a god");
        }
        message.add("\nRight click: shows god info");
        godDispatcher.HelperPopUp(event, message);
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
