package nativeapp.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import nativeapp.Game;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.misc.BASE64Decoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;

/**
 * Created by panpr on 03.12.2017.
 */
public class GameViewController {

    @FXML
    private FlowPane tileContainer;

    @FXML
    public void initialize() throws Exception {
//        GameTileController GTC = new GameTileController();
//        anchorPane.getChildren().add(GTC);
        ObservableList<Game> list = FXCollections.observableArrayList();

        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://localhost:8080/games");
        String response = resource.request(MediaType.APPLICATION_JSON).get(String.class);
        JSONParser parser = new JSONParser();

        Game game;

        try {
            JSONArray a = (JSONArray) parser.parse(response);

            for (Object o : a) {
                JSONObject obj = (JSONObject) o;
                game = new Game(
                        obj.get("title").toString(),
                        obj.get("description").toString(),
                        obj.get("version").toString(),
                        obj.get("icon").toString(),
                        obj.get("screenshot").toString()
                );

                BASE64Decoder base64Decoder = new BASE64Decoder();
                String baseCode = game.screenshot.toString().substring(24);
                ByteArrayInputStream screenshotIS = new ByteArrayInputStream(
                        base64Decoder.decodeBuffer(
                                baseCode
                        )
                );
                Image screenshot = new Image(screenshotIS);

                GameTile tile = new GameTile();
                tile.setTitle("WHAT");
                tile.setScreenshot(screenshot);
                tileContainer.getChildren().add(tile);

                GameTile tile1 = new GameTile();
                tile1.setTitle("WHAT");
                tileContainer.getChildren().add(tile1);

                GameTile tile2 = new GameTile();
                tile2.setTitle("WHAT");
                tileContainer.getChildren().add(tile2);

            }

        } catch (Exception e) {

        } finally {
            game = null;
        }




    }

}