package nativeapp.library;

import javafx.scene.image.Image;
import nativeapp.game.Game;
import nativeapp.utilities.B64Converter;
import nativeapp.views.tile.GameTile;

/**
 * Created by panpr on 06.12.2017.
 */
public class GameController {
    private Game game;
    private GameTile tile;

    public GameController(Game game, GameTile tile) {
        this.game = game;
        this.tile = tile;
        gameDataToTile();
    }

    public Game getGame() {
        return game;
    }

    public GameTile getTile() {
        return tile;
    }

    public void setupTile() throws Exception{
        Image screenshot =
                new Image(B64Converter.convertFile(game.screenshot, 24));
        Image icon =
                new Image(B64Converter.convertFile(game.icon, 23));

        tile.setTitle(game.title);
        tile.setIcon(icon);
        tile.setScreenshot(screenshot);
    }

    public void refreshTile() {
        if(game.hasUpdate()){
            tile.refreshUpdateButton();
        }
    }

    private void gameDataToTile() {
        this.tile.mergeWithGame(this.game);
    }


}
