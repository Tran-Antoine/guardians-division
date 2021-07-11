package net.starype.guardians_division.root_server.util;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Network;
import com.jme3.network.Server;
import net.starype.guardians_division.root_server.networking.IncomingGameServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CommunicationManager extends BaseAppState {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicationManager.class);
    private final AppStateManager stateManager;

    public CommunicationManager(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Players server instance
     */
    private Server server;
    /**
     * GameServers connection handler server instance
     */
    private IncomingGameServerHandler handler;

    /**
     * Loads the SocketServer for inter-server communications
     *
     * @throws IOException when server cannot be started
     */
    public void loadGameServerCommunicationServer() throws IOException {
        handler = new IncomingGameServerHandler(stateManager);
        handler.open();
    }

    /**
     * Loads the JME Server for player connections
     *
     * @throws IOException when server cannot be started
     */
    public void loadPlayerServer() throws IOException {
        int port = stateManager.getState(Configuration.class).getRootManagerPort();
        LOGGER.debug("Attempting to start root server on port: " + port);

        server = Network.createServer(port);
        server.start();
    }

    public Server getRootServer(){
        return server;
    }

    public IncomingGameServerHandler getGameServerHandler(){
        return handler;
    }

    @Override
    protected void initialize(Application app) {}
    @Override
    protected void cleanup(Application app) {
    }
    @Override
    protected void onEnable() {
    }
    @Override
    protected void onDisable() {
    }
}
