package net.starype.guardians_division.server_manager;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import net.starype.guardians_division.server_manager.networking.IncomingGameServerHandler;
import net.starype.guardians_division.server_manager.util.GameServersManager;
import net.starype.guardians_division.server_manager.util.PlayersServerRegisterer;
import net.starype.guardians_division.server_manager.util.ServerManagerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GuardiansDivisionServerManager extends SimpleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuardiansDivisionServerManager.class);

    /** Players server instance */
    private Server server;
    /** GameServers connection handler server instance */
    private IncomingGameServerHandler handler;

    GuardiansDivisionServerManager() {
        super.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        try {
            // Initialize ServerManager JSON configuration
            stateManager.attach(new ServerManagerConfiguration());
            // Initialize GameServerManager
            stateManager.attach(new GameServersManager(this, getMachineIP()));
            // Initialize ServerSocket for GameServers and JME Server for players to connect
            loadGameServerCommunicationServer();
            loadPlayerServer();
            PlayersServerRegisterer.registerPackets();
            PlayersServerRegisterer.loadListeners(this, server);
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Loads the SocketServer for inter-server communications
     * @throws IOException when server cannot be started
     */
    private void loadGameServerCommunicationServer() throws IOException {
        handler = new IncomingGameServerHandler(this);
        handler.open();
    }

    /**
     * Loads the JME Server for player connections
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

    /**
     * @return String of the machine internet IP.
     */
    private String getMachineIP() {
        String machineIP;
        try {
            machineIP = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())).readLine();
        } catch (IOException e) {
            machineIP = "127.0.0.1";
            LOGGER.warn("Machine IP is inaccessible ! Set IP to 127.0.0.1 !");
            LOGGER.error(e.getMessage());
        }
        LOGGER.debug("Machine IP: " + machineIP);
        return machineIP;
    }
}