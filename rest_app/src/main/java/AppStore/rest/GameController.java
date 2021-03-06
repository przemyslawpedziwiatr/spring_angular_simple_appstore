package AppStore.rest;

import AppStore.database.DbConnection;
import AppStore.games.Game;
import AppStore.games.GameDatabaseManager;
import org.h2.engine.Database;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@CrossOrigin(origins = "*")
@RestController
public class GameController  {
    private GameDatabaseManager GDB;

    public GameController() throws Exception{
        GDB = new GameDatabaseManager();
    }

    @RequestMapping("/games")
    public String game() throws Exception {
        return GDB.getGames();
    }
    
    @RequestMapping(value="/games/{id}", method= {RequestMethod.GET})
    public String gameById(@PathVariable(value="id") int id) throws Exception {
        return GDB.getGame(id);
    }

    @RequestMapping(value="/game/{hash}", method= {RequestMethod.GET})
    public String gameByHash(@PathVariable(value="hash") int hash) throws Exception {
        return GDB.getGameByHash(hash);

    }

    @RequestMapping(value="/games/{id}", method= {RequestMethod.PUT})
    public boolean updateGame(@PathVariable(value="id") int id, @RequestBody Game game) throws Exception {
        GDB.updateGame(id, game);
        return true;
    }

    @RequestMapping(value="/games", method= {RequestMethod.DELETE})
    public boolean deleteGame(@RequestParam(value="id") int id) throws Exception {
        GDB.removeGame(id);;
        return true;
    }
    
    @RequestMapping(value="/games", method= {RequestMethod.POST})
    public boolean addGame(@RequestBody Game game) throws Exception {
        GDB.addGame(game);
        return true;
    }

}
