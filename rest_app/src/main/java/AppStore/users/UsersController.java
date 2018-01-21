package AppStore.users;

import AppStore.database.DbConnection;
import AppStore.games.Game;
import AppStore.games.GameDatabaseManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@CrossOrigin(origins = "*")
@RestController
public class UsersController {
    private DbConnection db;

    public UsersController() throws Exception {
        db = new DbConnection();
    }

    @RequestMapping(value="/users/{name}/{pass}", method= {RequestMethod.GET})
    public boolean getUser(@PathVariable("name") String name,
                           @PathVariable("pass") String pass) throws Exception {

        PreparedStatement statement = db.getConn().prepareStatement(
                String.format("SELECT * FROM USERS WHERE NAME='%s' AND PASS='%s'", name, pass)
        );
        ResultSet result = statement.executeQuery();

        boolean exists = false;
        while (result.next()) {
            exists = true;
        }

        db.closeConnection();

        return exists;
    }

}
