package it.polimi.ingsw.PSP13.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class MainProva extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/mappa.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene scene = new Scene(pane,1280,720);
        scene.getStylesheets().add("mappa.css");

        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Santorini");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
