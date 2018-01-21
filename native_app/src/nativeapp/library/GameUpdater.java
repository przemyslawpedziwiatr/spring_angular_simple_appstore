package nativeapp.library;

import nativeapp.connector.GameConnector;
import nativeapp.game.Game;
import nativeapp.library.GameLibrary;
import org.json.simple.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameUpdater {
  
  private GameLibrary library;
  private GameConnector connector;
  
  public GameUpdater() {
  
  }
  
  public GameUpdater(GameLibrary library, GameConnector connector) {
    this.library = library;
    this.connector = connector;
  }
  
  public AtomicBoolean checkGameUpdateByHash() {
    return new AtomicBoolean(false);
  }
  
  public void checkGameUpdate(Game game) {
    AtomicBoolean gameHasUpdate = new AtomicBoolean();
    gameHasUpdate.set(false);
  
    library.getGames().forEach(
      gameController -> {
        try {
          JSONObject remoteGame = connector.gameByHash(
                  game.getDefault_hash().toString()
          );
        
          if (!remoteGame.isEmpty() && !game.getCurrent_version_hash().equalsIgnoreCase(
                  remoteGame.get("current_version_hash").toString())) {
            game.setHasUpdate(true);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
  }
  
  public AtomicBoolean checkUpdatesForLibrary(GameLibrary library, GameConnector connector) {
    AtomicBoolean anyUpdates = new AtomicBoolean();
    anyUpdates.set(false);
    
    library.getGames().forEach(
      gameController -> {
        try {
          Game game = gameController.getGame();
          JSONObject remoteGame = connector.gameByHash(
                  game.getDefault_hash().toString()
          );
          
          if (!remoteGame.isEmpty() && !game.getCurrent_version_hash().equalsIgnoreCase(
                  remoteGame.get("current_version_hash").toString())) {
            game.setHasUpdate(true);
            anyUpdates.set(true);
            gameController.refreshTile();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    );
    
    return anyUpdates;
  }
  
}
