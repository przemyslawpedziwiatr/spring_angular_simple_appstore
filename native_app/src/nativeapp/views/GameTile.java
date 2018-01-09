package nativeapp.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import nativeapp.game.Game;

import java.io.IOException;
import java.net.URL;

/**
 * Created by panpr on 05.12.2017.
 */
public class GameTile extends GridPane {
    private Node view;
    public Game game;
    private GameTileController controller;

    public void setTitle(String text) {
        controller.setTitle(text);
    }

    public void setScreenshot(Image image){
        controller.setScreenshot(image);
    }

    public void setIcon(Image image) {
        controller.setIcon(image);
    }

    public void mergeWithGame(Game game) {
        this.game = game;
        controller.setGame(game);
    }

    public void refreshUpdateButton() {
        controller.refreshUpdateButton();
    }
    public GameTile(){
        URL location = GameTile.class.getResource("../game_tile.fxml");


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return controller = new GameTileController();
            }
        });
        try {
            view = (Node) fxmlLoader.load();
            controller.setView(view);
        } catch (IOException ex) {
        }
        getChildren().add(view);
    }
}
