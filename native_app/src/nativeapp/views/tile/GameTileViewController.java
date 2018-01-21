package nativeapp.views.tile;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import nativeapp.game.Game;
import nativeapp.game.SelectedEvent;
import nativeapp.views.games.GameViewController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by panpr on 03.12.2017.
 */
public class GameTileViewController implements Initializable {

    private Node view;
    private Game game;

    @FXML
    Label game_title;
    @FXML
    GridPane TileGrid;
    @FXML
    ImageView game_screenshot, game_icon;
    @FXML
    Button downloadButton,  updateButton, playButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handlePlayClick(MouseEvent event) throws Exception {
        game.playGame();
    }

    @FXML
    public void displayGame(MouseEvent event) throws Exception {
        Event myEvent = new SelectedEvent(GameViewController.TILE_CLICK, game);
        this.view.fireEvent(myEvent);
    }

    @FXML
    public void handleDownloadClick(MouseEvent event) throws Exception {
        if(game.isLocal()){
            game.removeGame();
            downloadButton.setText("Download");
            updateButton.setVisible(false);
            playButton.setVisible(false);
        } else {
            game.downloadGame();
            game.setLocalFolder(game.title.replace(" ", "") + "_" + game.version);
            game.setLocalGameFilename("game.html");

            downloadButton.setText("Remove");
            playButton.setVisible(true);
            updateButton.setVisible(true);
            
            if(!game.hasUpdate()) {
                updateButton.setVisible(false);
            }
        }
    }

    @FXML
    public void handleUpdateClick(MouseEvent event) throws Exception {
        boolean isUpdated = game.updateGame();
        if(isUpdated){
            updateButton.setVisible(false);
        }
    }

    @FXML
    public void decreaseOpacity(MouseEvent event) throws Exception {
        game_screenshot.setOpacity(0.25);
    }

    @FXML
    public void increaseOpacity(MouseEvent event) throws Exception {
        game_screenshot.setOpacity(1.00);
    }

    public void refreshUpdateButton() {
        if(game.hasUpdate()){
            updateButton.setVisible(true);
        } else {
            updateButton.setVisible(false);
        }
    }
    public void setGame(Game game) {
        this.game = game;

        if(game.isLocal()) {
            downloadButton.setText("Remove");
            playButton.setVisible(true);
            updateButton.setVisible(false);

            if(game.hasUpdate()){
                updateButton.setVisible(true);
            }
        } else {
            updateButton.setVisible(false);
            playButton.setVisible(false);
        }
    }

    public void setView(Node view) {
        this.view = view;
    }
    
    public void setTitle(String text) {
        game_title.setText(text);
    }

    public void setScreenshot(Image image) {
        game_screenshot.setImage(image);
    }

    public void setIcon(Image image) {
        game_icon.setImage(image);
    }
    
}
