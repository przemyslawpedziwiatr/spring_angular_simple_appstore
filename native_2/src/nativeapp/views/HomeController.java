package nativeapp.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nativeapp.game.Game;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;

public class HomeController {

    @FXML
    private Button GameButton;
    @FXML
    private ListView<Game> GameList;
    @FXML
    private Label title, version, description;
    @FXML
    private ImageView screenshot;

    public HomeController() {

    }

    @FXML
    public void initialize() {
        ObservableList<Game> list = FXCollections.observableArrayList();

        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("http://localhost:8080/games");
        String response = resource.request(MediaType.APPLICATION_JSON).get(String.class);
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(response);

            for (Object o : a) {
                JSONObject obj = (JSONObject) o;
                Game game = new Game(
                        obj.get("title").toString(),
                        obj.get("description").toString(),
                        obj.get("version").toString(),
                        obj.get("icon_url").toString(),
                        obj.get("screenshot_url").toString()
                );
                list.add(game);
            }

        } catch (Exception e) {

        }

        GameList.setItems(list);


        GameList.setCellFactory(param -> new ListCell<Game>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        BASE64Decoder base64Decoder = new BASE64Decoder();
                        String baseCode = game.icon.toString().substring(23);
                        ByteArrayInputStream iconIS = new ByteArrayInputStream(
                                base64Decoder.decodeBuffer(
                                        baseCode
                                )
                        );
                        Image icon = new Image(iconIS);

                        imageView.setImage(icon);
                        imageView.setFitWidth(20);
                        imageView.setPreserveRatio(true);
                        setText(game.title.toString());
                        setGraphic(imageView);
                    } catch (Exception e) {

                    }
                }
            }

        });

    }


    @FXML
    private void handleButtonClick(MouseEvent event) throws Exception {
        Game game = GameList.getSelectionModel().getSelectedItem();
        title.setText(game.title);
        version.setText(game.version);
        description.setText(game.description);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        String baseCode = game.screenshot.toString().substring(24);
        ByteArrayInputStream screenshotIS = new ByteArrayInputStream(
                base64Decoder.decodeBuffer(
                        baseCode
                )
        );
        Image screenshotDecoded = new Image(screenshotIS);
        screenshot.setImage(screenshotDecoded);
    }
}
