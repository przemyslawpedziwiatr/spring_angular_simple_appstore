package nativeapp.library;

import nativeapp.connector.GameConnector;
import nativeapp.data.GamesDataController;
import nativeapp.game.Game;
import nativeapp.views.tile.GameTile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameManager {
  
  private static GameUpdater updater;
  private static GameLibrary library;
  private static GameConnector connector;
  private static GamesDataController controller;
  
  private static AtomicBoolean hasUpdates = new AtomicBoolean(false);
  
  static {
    try {
      updater = new GameUpdater();
      library = new GameLibrary();
      controller = new GamesDataController();
      connector = new GameConnector();
    } catch (Exception e) {
    
    }
  }
  
  public static void runManager() {
    prepareGames();
    checkUpdates();
  }
  
  public static void prepareGames() {
    try {
      JSONArray remoteGames = fetchRemoteGamesWithoutLocal();
      addGamesToLibrary(remoteGames);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static JSONArray fetchRemoteGamesWithoutLocal() throws Exception {
    JSONArray localGames = controller.localGames();
    JSONArray remoteGames = connector.games();
    return filterOutRemoteGamesFromLocal(localGames, remoteGames);
  }
  
  private static JSONArray filterOutRemoteGamesFromLocal(JSONArray localGames, JSONArray remoteGames) {
    JSONArray filteredGames = new JSONArray();
    
    if (!localGames.isEmpty() && !remoteGames.isEmpty()) {
      for (Object localGameObject : localGames) {
        JSONObject localGame = (JSONObject) localGameObject;
        filteredGames.add(localGame);
        
        int remoteGameToRemoveIndex = -1;
        int index = 0;
        for (Object remoteGameObject : remoteGames) {
          JSONObject remoteGame = (JSONObject) remoteGameObject;
          
          if (remoteGame.get("default_hash").toString().equalsIgnoreCase(
                  localGame.get("default_hash").toString()
          )) {
            remoteGameToRemoveIndex = index;
            break;
          }
          index++;
        }
        
        if (remoteGameToRemoveIndex != -1) {
          remoteGames.remove(remoteGameToRemoveIndex);
        }
      }
      
      filteredGames.addAll(remoteGames);
      
    } else if (!remoteGames.isEmpty()) {
      return remoteGames;
    } else {
      return localGames;
    }
    
    return filteredGames;
  }
  
  public static void addGamesToLibrary(JSONArray games) {
    for (Object game : games) {
      JSONObject gameJSON = (JSONObject) game;
      
      Game tempGame = new Game(
        gameJSON.get("title").toString(),
        gameJSON.get("description").toString(),
        gameJSON.get("version").toString(),
        gameJSON.get("icon").toString(),
        gameJSON.get("screenshot").toString()
      );
      
      if (gameJSON.containsKey("download_dir_name")) {
        tempGame.setLocal(true);
        tempGame.setLocalFolder(gameJSON.get("download_dir_name").toString());
        tempGame.setLocalGameFilename("game.html");
      } else {
        tempGame.setFile(gameJSON.get("file").toString());
      }
      
      tempGame.setDefault_hash(gameJSON.get("default_hash").toString());
      tempGame.setCurrent_version_hash(gameJSON.get("current_version_hash").toString());
      
      library.addGame(tempGame, new GameTile());
    }
  }
  
//  public static boolean checkGameHasUpdate(Game game) {
//      return updater.checkGameUpdate(game);
//  }
  
  public static boolean hasGameUpdates() {
    return hasUpdates.get();
  }
  
  public static AtomicBoolean checkUpdates() {
    hasUpdates = updater.checkUpdatesForLibrary(library, connector);
    return hasUpdates;
  }
  
  public static List<GameController> getGames() {
    return library.getGames();
  }
  
}
