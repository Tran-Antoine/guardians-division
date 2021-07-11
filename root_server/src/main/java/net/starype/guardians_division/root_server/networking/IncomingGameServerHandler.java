package net.starype.guardians_division.root_server.networking;

import com.jme3.app.state.AppStateManager;
import net.starype.guardians_division.root_server.game_server.GameServer;
import net.starype.guardians_division.root_server.util.GameServersManager;
import net.starype.guardians_division.root_server.util.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class IncomingGameServerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingGameServerHandler.class);

    private AppStateManager appStateManager;

    private ServerSocket server;
    private Thread handlerThread;

    private boolean running;

    public IncomingGameServerHandler(AppStateManager appStateManager) throws IOException {
        this.appStateManager = appStateManager;
        server = new ServerSocket(appStateManager.getState(Configuration.class).getGameServerHandlerPort());
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

                    Optional<GameServer> gameServer = appStateManager.getState(GameServersManager.class)
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
        LOGGER.debug("Listening to connections...");
    }

    public void close() {
        running = false;
    }
}
