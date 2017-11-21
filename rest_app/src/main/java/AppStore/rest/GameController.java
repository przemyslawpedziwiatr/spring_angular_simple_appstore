package AppStore.rest;

import AppStore.database.DbConnection;
import AppStore.games.GameDTO;
import AppStore.games.GameDatabaseManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
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

    @RequestMapping(value="/games/{id}", method= {RequestMethod.PUT})
    public boolean updateGame(@PathVariable(value="id") int id, @RequestBody GameDTO game) throws Exception {
        GDB.updateGame(id, game.title, game.version, game.description, game.screenshot_url, game.icon_url);
        return true;
    }

    @RequestMapping(value="/games", method= {RequestMethod.DELETE})
    public boolean deleteGame(@RequestParam(value="id") int id) throws Exception {
        GDB.removeGame(id);
        return true;
    }

    
    @RequestMapping(value="/games", method= {RequestMethod.POST})
    public boolean addGame(@RequestBody GameDTO game) throws Exception {
        GDB.addGame(game.title, game.version, game.description, game.screenshot_url, game.icon_url);
        return true;
    }
}
