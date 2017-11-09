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
                node.put("icon_url", result.getString("icon_url"));
                node.put("version", result.getString("version"));
                node.put("description", result.getString("description"));
                node.put("screenshot_url", result.getString("screenshot_url"));
            }

            db.closeConnection();

            return node.toString();
        }
        return "";
    }

    public boolean updateGame(int id, String title, String description, String icon_url, String version, String screenshot_url)
            throws Exception {
        if (id != -1) {

            PreparedStatement statement = db.getConn().prepareStatement(
                    String.format("UPDATE GAMES SET "
                                    + "TITLE = '%s',"
                                    + "ICON_URL = '%s',"
                                    + "VERSION = '%s',"
                                    + "DESCRIPTION = '%s',"
                                    + "SCREENSHOT_URL = '%s' "
                                    + "WHERE id = '%d'",
                            title, icon_url, version, description, screenshot_url, id)
            );
            statement.execute();
            db.closeConnection();
            return true;
        }
        db.closeConnection();
        return false;
    }


    public String getGames() throws Exception {
        PreparedStatement statement = db.getConn().prepareStatement("SELECT id, title, icon_url, version, description, screenshot_url FROM GAMES");
        ResultSet result = statement.executeQuery();

        ArrayList<ObjectNode> nodes = new ArrayList();

        while (result.next()) {
            ObjectNode node = new ObjectMapper().createObjectNode();

            node.put("id", result.getInt("id"));
            node.put("title", result.getString("title"));
            node.put("icon_url", result.getString("icon_url"));
            node.put("version", result.getString("version"));
            node.put("description", result.getString("description"));
            node.put("screenshot_url", result.getString("screenshot_url"));

            nodes.add(node);
        }

        db.closeConnection();
        return nodes.toString();
    }

    private int getLastGameId() throws Exception {
        PreparedStatement statement = db.getConn().prepareStatement("SELECT ID FROM GAMES ORDER BY ID DESC LIMIT 1");
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            db.closeConnection();
            return result.getInt("ID");
        }
        return -1;
    }

    public boolean addGame(String title, String description, String icon_url, String version, String screenshot)
            throws Exception {
        int lastId = getLastGameId();

        if (lastId != -1) {

            icon_url = icon_url.isEmpty() ?
                    "https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-game-controller-b-128.png"
                    : icon_url;
            screenshot = screenshot.isEmpty() ?
                    "https://i.kinja-img.com/gawker-media/image/upload/t_original/vdlqqt4tb2ycrwr3ucru.jpg"
                    : screenshot;

            PreparedStatement statement = db.getConn().prepareStatement(
                    String.format("INSERT INTO GAMES "
                                    + "VALUES (%d, '%s', '%s', '%s', '%s', '%s')",
                            lastId + 1, title, icon_url, version, description, screenshot)
            );
            statement.execute();
            db.closeConnection();
            return true;
        }
        return false;
    }

}
