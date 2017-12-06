package nativeapp.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by panpr on 03.12.2017.
 */
public class GameTileController implements Initializable {

    @FXML
    Label game_title;

    @FXML
    GridPane TileGrid;

    @FXML
    ImageView game_screenshot;

    public GameTileController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game_title.setText("OMG");
    }

    public void setTitle(String text) {
        game_title.setText(text);
    }

    public void setScreenshot(Image image) {
        game_screenshot.setImage(image);
    }
}
