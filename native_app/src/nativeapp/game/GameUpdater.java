package nativeapp.game;

import org.json.simple.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameUpdater {

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

}
