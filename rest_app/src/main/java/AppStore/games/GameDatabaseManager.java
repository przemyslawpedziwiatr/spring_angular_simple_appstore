package AppStore.games;

import AppStore.database.DbConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by panpr on 09.11.2017.
 */
public class GameDatabaseManager {

    private DbConnection db;

    public GameDatabaseManager() throws Exception {
        db = new DbConnection();
    }

    public void removeGame(int id) throws Exception {
        if (id != -1) {
            PreparedStatement statement = db.getConn().prepareStatement(
                    String.format("DELETE FROM GAMES WHERE ID=%d", id)
            );
            statement.execute();
        }
        db.closeConnection();
    }

    public String getGame(int id) throws Exception {
        if (id != -1) {
            PreparedStatement statement = db.getConn().prepareStatement(
                    String.format("SELECT * FROM GAMES WHERE ID=%d", id)
            );
            ResultSet result = statement.executeQuery();

            ObjectNode node = new ObjectMapper().createObjectNode();

            while (result.next()) {
                node.put("id", result.getInt("id"));
                node.put("title", result.getString("title"));
                node.put("icon", result.getString("icon"));
                node.put("version", result.getString("version"));
                node.put("description", result.getString("description"));
                node.put("screenshot", result.getString("screenshot"));
                node.put("file", result.getString("file"));
                node.put("default_hash", result.getString("default_hash"));
                node.put("current_version_hash", result.getString("current_version_hash"));
            }

            db.closeConnection();

            return node.toString();
        }
        return "";
    }

    public String getGameByHash(int hash) throws Exception {
        if (hash != -1) {
            PreparedStatement statement = db.getConn().prepareStatement(
                    String.format("SELECT * FROM GAMES WHERE DEFAULT_HASH=%d", hash)
            );
            ResultSet result = statement.executeQuery();

            ObjectNode node = new ObjectMapper().createObjectNode();

            while (result.next()) {
                node.put("id", result.getInt("id"));
                node.put("title", result.getString("title"));
                node.put("icon", result.getString("icon"));
                node.put("version", result.getString("version"));
                node.put("description", result.getString("description"));
                node.put("screenshot", result.getString("screenshot"));
                node.put("file", result.getString("file"));
                node.put("default_hash", result.getString("default_hash"));
                node.put("current_version_hash", result.getString("current_version_hash"));
            }

            db.closeConnection();

            return node.toString();
        }
        return "";
    }


    public boolean updateGame(int id, Game game)
            throws Exception {
        if (id != -1) {

            PreparedStatement statement = db.getConn().prepareStatement(
                    String.format("UPDATE GAMES SET "
                                    + "TITLE = '%s', "
                                    + "ICON = '%s', "
                                    + "VERSION = %d, "
                                    + "DESCRIPTION = '%s', "
                                    + "SCREENSHOT = '%s', "
                                    + "FILE= '%s', "
                                    + "CURRENT_VERSION_HASH= %d"
                                    + "WHERE id = '%d'",
                            game.title,
                            game.icon_b64,
                            Integer.parseInt(game.version),
                            game.description,
                            game.screenshot_b64,
                            game.file_b64,
                            this.hashCode(),
                            id)
            );
            statement.execute();
            db.closeConnection();
            return true;
        }
        db.closeConnection();
        return false;
    }


    public String getGames() throws Exception {
        PreparedStatement statement = db.getConn().prepareStatement(
                "SELECT id, title, icon, version, description, screenshot, file, " +
                        "default_hash, current_version_hash FROM GAMES");
        ResultSet result = statement.executeQuery();

        ArrayList<ObjectNode> nodes = new ArrayList();

        while (result.next()) {
            ObjectNode node = new ObjectMapper().createObjectNode();

            node.put("id", result.getInt("id"));
            node.put("title", result.getString("title"));
            node.put("icon", result.getString("icon"));
            node.put("version", result.getString("version"));
            node.put("description", result.getString("description"));
            node.put("screenshot", result.getString("screenshot"));
            node.put("file", result.getString("file"));
            node.put("default_hash", result.getString("default_hash"));
            node.put("current_version_hash", result.getString("current_version_hash"));

            nodes.add(node);
        }

        db.closeConnection();
        if(!nodes.isEmpty()) {
            return nodes.toString();
        }
        return "";
    }

    private int getLastGameId() throws Exception {
        PreparedStatement statement = db.getConn().prepareStatement("SELECT ID FROM GAMES ORDER BY ID DESC LIMIT 1");
        ResultSet result = statement.executeQuery();
        int returnedResult = -1;
        while (result.next()) {
            returnedResult = result.getInt("ID");
        }
        db.closeConnection();
        return returnedResult;
    }

    public boolean addGame(Game game)
            throws Exception {

        String sql = String.format("INSERT INTO GAMES(title, icon, version, description, screenshot, file, default_hash, current_version_hash) "
                        + "VALUES ('%s', '%s', %d, '%s', '%s','%s', %d, %d)",
                game.title,
                game.icon_b64,
                Integer.parseInt(game.version),
                game.description,
                game.screenshot_b64,
                game.file_b64,
                game.hashCode(),
                game.hashCode());

        PreparedStatement statement = db.getConn().prepareStatement(sql);
        statement.execute();
        db.closeConnection();

        return true;
    }

}
