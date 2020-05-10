package it.polimi.ingsw.PSP13.view.GUI;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Javafx {

    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane parent;
    @FXML
    private AnchorPane slide;
    @FXML
    private TextField serverText;
    @FXML
    private TextField nicknameText;
    @FXML
    private Button ok;
    @FXML
    private Button okSlide;

    @FXML
    public void gameStart(ActionEvent event) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/game.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene gameBoard = new Scene(pane);
        gameBoard.getStylesheets().add("GameStyle.css");

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(gameBoard);
    }

    @FXML
    public void minimizeApp(MouseEvent event)
    {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void closeApp(MouseEvent event)
    {
        System.exit(0);
    }

    @FXML
    public void textCheckNickname(KeyEvent event)
    {
        if(nicknameText.getText().equals(""))
        {
            okSlide.getStyleClass().clear();
            okSlide.getStyleClass().add("textEmpty");
            okSlide.setDisable(true);

        }
        else
        {
            okSlide.getStyleClass().remove("textEmpty");
            okSlide.getStyleClass().add("textFilled");
            okSlide.setDisable(false);
        }
    }

    @FXML
    public void textCheck(KeyEvent event)
    {
        if(serverText.getText().equals(""))
         {
             ok.getStyleClass().clear();
             ok.getStyleClass().add("textEmpty");
             ok.setDisable(true);

         }
        else
        {
            ok.getStyleClass().remove("textEmpty");
            ok.getStyleClass().add("textFilled");
            ok.setDisable(false);
        }

    }

    @FXML
    public void connect(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("javafx.fxml"));
        Scene scene = ((Node)event.getSource()).getScene();

        root.translateXProperty().set(scene.getWidth());
        parent.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(container.translateXProperty(),230, Interpolator.EASE_IN);

        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            slide.setVisible(true);
        });
        timeline.play();

        serverText.setFocusTraversable(false);
        nicknameText.requestFocus();

    }

}
