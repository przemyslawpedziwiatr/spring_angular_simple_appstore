package nativeapp.game;

import nativeapp.data.GamesDataController;
import nativeapp.utilities.DesktopApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.net.URI;

/**
 * Created by panpr on 19.11.2017.
 */
public class Game {
    public String title, description, version, icon, screenshot, file;
    public String default_hash, current_version_hash;

    private boolean isLocal = false;
    private String localFolder = "";
    private String localGameFilename = "";

    private boolean hasUpdate = false;

    // Constructor

    public Game(String title, String description, String version, String icon, String screenshot) {
        this.title = title;
        this.description = description;
        this.version = version;
        this.icon = icon;
        this.screenshot = screenshot;
    }

    public Game(String title, String description, String version, String icon, String screenshot, String file_b64) {
        this.title = title;
        this.description = description;
        this.version = version;
        this.icon = icon;
        this.screenshot = screenshot;
        this.file = file_b64;
    }

    // Important methods

    public void downloadGame() {
        try {
            GamesDataController.addGame(this);
            setLocal(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playGame() {
        String filePath = "file:///" + GamesDataController.getHomeFolder() + "/" +
                GamesDataController.getFolderName() +
                GamesDataController.getGameFolder() + "/" +
                localFolder + "/" + localGameFilename;

        try {
            URI gameUri = new URI(filePath.replace("\\","/"));
            DesktopApi.browse(gameUri);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean updateGame() {
        try {
            JSONParser parser = new JSONParser();
            Client client = ClientBuilder.newClient();
            WebTarget resource = client.target("http://localhost:8080/game/" + default_hash);

            String response = resource.request(MediaType.APPLICATION_JSON).get(String.class);

            JSONObject updatedGame;
            updatedGame = (JSONObject) parser.parse(response);

            this.removeGame();

            this.file = updatedGame.get("file").toString();
            this.version = updatedGame.get("version").toString();
            this.current_version_hash = updatedGame.get("current_version_hash").toString();

            this.downloadGame();

            return true;
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void removeGame() {
        try {
            GamesDataController.removeGame(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Getters setters
    public String getLocalFolder() {
        return localFolder;
    }

    public void setLocalFolder(String localFolder) {
        this.localFolder = localFolder;
    }

    public String getLocalGameFilename() {
        return localGameFilename;
    }

    public void setLocalGameFilename(String localGameFile) {
        this.localGameFilename = localGameFile;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public void setFile(String file_b64) {
        this.file = file_b64;
    }


    public String getDefault_hash() {
        return default_hash;
    }

    public void setDefault_hash(String default_hash) {
        this.default_hash = default_hash;
    }

    public String getCurrent_version_hash() {
        return current_version_hash;
    }

    public void setCurrent_version_hash(String current_version_hash) {
        this.current_version_hash = current_version_hash;
    }


    public boolean hasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

}
