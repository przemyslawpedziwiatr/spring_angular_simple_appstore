package nativeapp.library;

import nativeapp.game.Game;
import nativeapp.views.GameTile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panpr on 06.12.2017.
 */
public class GameLibrary {
    private String libraryPath;

    private ArrayList<GameController> games;

    public GameLibrary() {
        games = new ArrayList<>();
    }

    public void getDataFromLibrary(){

    }

    public List<GameController> getGames() {
        return games;
    }

    public void addGame(Game game, GameTile gameTile){
        GameController GC = new GameController(game, gameTile);
        try {
            GC.setupTile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        games.add(GC);
    }

}
