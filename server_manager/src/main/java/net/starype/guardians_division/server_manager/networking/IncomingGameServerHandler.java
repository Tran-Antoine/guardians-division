package net.starype.guardians_division.server_manager.networking;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import net.starype.guardians_division.server_manager.game_server.GameServer;
import net.starype.guardians_division.server_manager.util.GameServersManager;
import net.starype.guardians_division.server_manager.util.ServerManagerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class IncomingGameServerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingGameServerHandler.class);

    private SimpleApplication main;

    private ServerSocket server;
    private Thread handlerThread;

    private boolean running;

    public IncomingGameServerHandler(SimpleApplication main) throws IOException {
        this.main = main;
        server = new ServerSocket(getManager(ServerManagerConfiguration.class).getGameServerListenerPort());
    }

    /*
     WRITE TO: PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
     READ: BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    */

    public void open() {
        running = true;
        handlerThread = new Thread(() -> {
            while (running) {
                try {
                    Socket gameServerClient = server.accept();
                    LOGGER.debug("REMOTE ADDRESS: {}", gameServerClient.getRemoteSocketAddress().toString());
                    LOGGER.debug("PORT: {}", gameServerClient.getPort());

                    Optional<GameServer> gameServer = getManager(GameServersManager.class)
                            .getServer(gameServerClient.getRemoteSocketAddress(), gameServerClient.getPort());

                    LOGGER.debug("IS SERVER EXISTING? {}", gameServer.isPresent());

                    gameServer.ifPresent(value -> value.setCommunicationSocket(gameServerClient));
                    //TODO: Handle server data
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        handlerThread.start();
        LOGGER.info("ServerSocket started ! Listening to connections...");
    }

    private <T extends AppState> T getManager(Class<T> state){
        return main.getStateManager().getState(state);
    }

    public void close() {
        running = false;
    }
}
