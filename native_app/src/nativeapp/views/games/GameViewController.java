package nativeapp.views.games;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import nativeapp.chat.Message;
import nativeapp.chat.Server;
import nativeapp.game.Game;
import nativeapp.game.SelectedEvent;
import nativeapp.library.GameManager;
import nativeapp.utilities.B64Converter;

/**
 * Created by panpr on 03.12.2017.
 */
public class GameViewController {
  
  public static EventType<SelectedEvent> TILE_CLICK = new EventType<>("SELECTED");

  @FXML
  private TextArea chatArea;
  @FXML
  private TextField chatMessage;
  @FXML
  Node GameView;
  @FXML
  private FlowPane tileContainer;
  @FXML
  private Label game_detail_title, game_detail_description, notification;
  @FXML
  private ImageView game_detail_icon, game_detail_screenshot;
  
  @FXML
  public void initialize() throws Exception {
    setupGames();
    setupChat();
  }
  
  private void setupGames() {
    
    GameView.addEventHandler(GameViewController.TILE_CLICK,
            event -> showDetails(event));
    
    GameManager.runManager();
    addGamesToView();
    
    if (GameManager.checkUpdates().get()) {
      notification.setText("New updates available!");
    }
  }
  
  public void addGamesToView() {
    GameManager.getGames().forEach(game -> {
      tileContainer.getChildren().add(game.getTile());
    });
  }
  
  public void showDetails(SelectedEvent event) {
    Game tempGame = event.getGame();
    
    try {
      game_detail_title.setText(tempGame.title);
      game_detail_description.setText(tempGame.description);
      
      Image icon = new Image(B64Converter.convertFile(tempGame.icon, 23));
      game_detail_icon.setImage(icon);
      
      Image screenshot = new Image(B64Converter.convertFile(tempGame.screenshot, 24));
      game_detail_screenshot.setImage(screenshot);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void setupChat() {
    Server.server.addEventListener("send", Message.class, new DataListener<Message>() {
      
      @Override
      public void onData(SocketIOClient client, Message data, AckRequest ackSender) throws Exception {
        chatArea.setText(chatArea.getText() +
                "\n Admin: " + data.getMessage());
        chatArea.appendText("");
        chatArea.setScrollTop(Double.MAX_VALUE);
        
        Server.server.getBroadcastOperations().sendEvent("message", data);
      }
    });
    
    chatMessage.setText("");
  }
  
  private void sendMessage() {
    String message = chatMessage.getText();
    chatArea.setText(chatArea.getText() +
            "\n You: " + chatMessage.getText());
    chatMessage.setText("");
    
    Server.server.getAllClients().iterator().forEachRemaining(
      consumer -> {
        System.out.println(consumer.getRemoteAddress());
        consumer.sendEvent("message", new Message("User", message));
      }
    );
    
    chatArea.appendText("");
    chatArea.setScrollTop(Double.MAX_VALUE);
  }
  
  @FXML
  private void sendTextKey(KeyEvent event) throws Exception {
    if (event.getEventType() == KeyEvent.KEY_PRESSED
            && event.getCode() == KeyCode.ENTER) {
      sendMessage();
    }
  }
  
  @FXML
  private void sendText(MouseEvent event) throws Exception {
    sendMessage();
  }
}