package net.starype.guardians_division.server_manager;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.service.serializer.ServerSerializerRegistrationsService;
import com.jme3.system.JmeContext;
import net.starype.guardians_division.server_manager.networking.IncomingGameServerHandler;
import net.starype.guardians_division.server_manager.util.GameServersManager;
import net.starype.guardians_division.server_manager.util.PlayersServerRegisterer;
import net.starype.guardians_division.server_manager.util.ServerManagerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GuardiansDivisionServerManager extends SimpleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuardiansDivisionServerManager.class);
    /**
     * Players server instance
     */
    private Server server;
    /**
     * GameServers connection handler server instance
     */
    private IncomingGameServerHandler handler;

    GuardiansDivisionServerManager() {
        super.start(JmeContext.Type.Headless);
    }

    public static void main(String... args) {
        new GuardiansDivisionServerManager();
    }

    @Override
    public void simpleInitApp() {
        attachStates();
        PlayersServerRegisterer.registerPackets();
        try {
            // Initialize ServerSocket for GameServers and JME Server for players to connect
            loadGameServerCommunicationServer();
            loadPlayerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlayersServerRegisterer.loadListeners(this, server);
    }

    private void attachStates() {
        // Initialize ServerManager JSON configuration
        stateManager.attach(new ServerManagerConfiguration());
        // Initialize GameServerManager
        stateManager.attach(new GameServersManager(this));
    }

    /**
     * Loads the SocketServer for inter-server communications
     *
     * @throws IOException when server cannot be started
     */
    private void loadGameServerCommunicationServer() throws IOException {
        handler = new IncomingGameServerHandler(this);
        handler.open();
    }

    /**
     * Loads the JME Server for player connections
     *
     * @throws IOException when server cannot be started
     */
    private void loadPlayerServer() throws IOException {
        int port = stateManager.getState(ServerManagerConfiguration.class).getServerManagerPort();
        LOGGER.debug("Attempting to start server on port: " + port);

        server = Network.createServer(port);

        server.start();
        LOGGER.debug("Started server");
    }

    /**
     * Closes game servers and server manager
     */
    @Override
    public void destroy() {
        server.close();
        handler.close();
        // TODO: Save existing game servers to handle them after reboot.
        //getStateManager().getState(GameServersManager.class).getServers().forEach(server -> server.getServer().stopServer());
        super.destroy();
    }
}