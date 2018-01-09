package nativeapp.chat;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

/**
 *
 * @author Krzysztof Jelonek
 */
public class Server {

    public static SocketIOServer server;
    public static SocketIOClient client;

    public static void runServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3700);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("onConnected");
                client.sendEvent("message", new Message("", "Welcome to the chat!"));
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("onDisconnected");
            }
        });



        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started");

    }

}