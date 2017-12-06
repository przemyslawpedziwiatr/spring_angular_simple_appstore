package nativeapp.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

/**
 * Created by panpr on 05.12.2017.
 */
public class GameTile extends GridPane {
    private Node view;
    private GameTileController controller;

    public void setTitle(String text) {
        controller.setTitle(text);
    }

    public void setScreenshot(Image image){
        controller.setScreenshot(image);
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

        } catch (IOException ex) {
        }
        getChildren().add(view);
    }
}
