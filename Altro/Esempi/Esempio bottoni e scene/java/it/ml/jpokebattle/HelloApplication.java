package it.ml.jpokebattle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));

        Scene scene = new Scene(root, 700, 500);
        Image icon = new Image(new File("C:/Users/halle/Desktop/JPokeBattle/Images/pokemon.png").toURI().toString());

        stage.setTitle("Hello!");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }


}