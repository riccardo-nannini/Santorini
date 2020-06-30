package it.polimi.ingsw.PSP13.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Main extends Application {

    /**
     * starts the javaFX main thread
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Font.loadFont(Main.class.getResource("/fonts/RobotoCondensed-Regular.ttf").toExternalForm(), 18);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/lobby.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("lobby.css");

        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Santorini");
        primaryStage.getIcons().add(new Image("island.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
