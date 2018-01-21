package nativeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nativeapp.chat.Server;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        startChat();
        startGUI(primaryStage);
    }

    private void startChat() {
        Server.runServer();
    }

    private void startGUI(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("game_view.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
