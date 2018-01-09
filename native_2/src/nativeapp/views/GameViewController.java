package nativeapp.views;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import nativeapp.game.SelectedEvent;
import nativeapp.connector.GameConnector;
import nativeapp.data.GamesDataController;
import nativeapp.game.Game;
import nativeapp.library.GameLibrary;
import nativeapp.utilities.B64Converter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by panpr on 03.12.2017.
 */
public class GameViewController {

    private GameLibrary gameLibrary;
    GamesDataController gamesDataController;
    private GameConnector gameConnector;

    public static EventType<SelectedEvent> TILE_CLICK = new EventType<>("SELECTED");

    @FXML
    Node GameView;
    @FXML
    private FlowPane tileContainer;
    @FXML
    private Label game_detail_title, game_detail_description, notification;
    @FXML
    private ImageView game_detail_icon, game_detail_screenshot;

    @FXML
    public void initialize() throws Exception {
        gameLibrary = new GameLibrary();
        gamesDataController = new GamesDataController();
        gameConnector = new GameConnector();

        GameView.addEventHandler(GameViewController.TILE_CLICK,
                event -> showDetails(event));

        JSONArray localGames = gamesDataController.localGames();
        JSONArray remoteGames = gameConnector.games();
        JSONArray filteredGames = filterOutRemoteGamesFromLocal(localGames, remoteGames);

        addGamesToLibrary(filteredGames);
        AtomicBoolean newUpdates = checkUpdates();
        addGamesToView();


        if(newUpdates.get()) {
            notification.setText("New updates available!");
        }
    }

    private AtomicBoolean checkUpdates() {
        AtomicBoolean anyUpdates = new AtomicBoolean();
        anyUpdates.set(false);

        gameLibrary.getGames().forEach(
            gameController -> {
                try {
                    Game game = gameController.getGame();
                    JSONObject remoteGame = gameConnector.gameByHash(
                            game.getDefault_hash().toString()
                    );

                    if(!remoteGame.isEmpty() && !game.getCurrent_version_hash().equalsIgnoreCase(
                            remoteGame.get("current_version_hash").toString())) {
                        game.setHasUpdate(true);
                        anyUpdates.set(true);
                        gameController.refreshTile();
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        );

        return anyUpdates;
    }

    private JSONArray filterOutRemoteGamesFromLocal(JSONArray localGames, JSONArray remoteGames) {
        JSONArray filteredGames = new JSONArray();

        if(!localGames.isEmpty() && !remoteGames.isEmpty()) {
            for(Object localGameObject : localGames) {
                JSONObject localGame = (JSONObject) localGameObject;
                filteredGames.add(localGame);

                int remoteGameToRemoveIndex = -1;
                int index = 0;
                for(Object remoteGameObject: remoteGames) {
                    JSONObject remoteGame = (JSONObject) remoteGameObject;

                    if(remoteGame.get("default_hash").toString().equalsIgnoreCase(
                            localGame.get("default_hash").toString()
                    )){
                        remoteGameToRemoveIndex = index;
                        break;
                    }
                    index ++;
                }

                if(remoteGameToRemoveIndex != -1 ){
                    remoteGames.remove(remoteGameToRemoveIndex);
                }
            }

            filteredGames.addAll(remoteGames);

        } else if(!remoteGames.isEmpty()) {
            return remoteGames;
        } else {
            return localGames;
        }

        return filteredGames;
    }

    public void showDetails(SelectedEvent event) {
        Game tempGame = event.getGame();

        try {
            game_detail_title.setText(tempGame.title);
            game_detail_description.setText(tempGame.description);

            Image icon = new Image(B64Converter.convertFile(tempGame.icon, 23));
            game_detail_icon.setImage(icon);

            Image screenshot = new Image(B64Converter.convertFile(tempGame.screenshot, 24));
            game_detail_screenshot.setImage(screenshot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGamesToLibrary(JSONArray games) {
        for (Object game : games) {
            JSONObject gameJSON = (JSONObject) game;

            Game tempGame = new Game(
                    gameJSON.get("title").toString(),
                    gameJSON.get("description").toString(),
                    gameJSON.get("version").toString(),
                    gameJSON.get("icon").toString(),
                    gameJSON.get("screenshot").toString()
            );

            if(gameJSON.containsKey("download_dir_name")) {
                tempGame.setLocal(true);
                tempGame.setLocalFolder(gameJSON.get("download_dir_name").toString());
                tempGame.setLocalGameFilename("game.html");
            } else {
                tempGame.setFile(gameJSON.get("file").toString());
            }

            tempGame.setDefault_hash(gameJSON.get("default_hash").toString());
            tempGame.setCurrent_version_hash(gameJSON.get("current_version_hash").toString());

            gameLibrary.addGame(tempGame, new GameTile());
        }
    }

    public void addGamesToView() {
        gameLibrary.getGames().forEach(
            (game) -> {
                tileContainer.getChildren().add(game.getTile());
            });

    }
}