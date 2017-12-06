package nativeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nativeapp.files.FileController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getResource("game_view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        FileController fileC = new FileController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
