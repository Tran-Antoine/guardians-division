package net.starype.guardians_division.server_manager.networking;

import com.jme3.app.SimpleApplication;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import net.starype.guardians_division.server_manager.game_server.GameServer;
import net.starype.guardians_division.server_manager.util.GameServersManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GDConnectionListener implements ConnectionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GDConnectionListener.class);
    private final GameServersManager gameServersManager;

    public GDConnectionListener(SimpleApplication main) {
        this.gameServersManager = main.getStateManager().getState(GameServersManager.class);
    }

    /**
     * Called when a connection is incoming
     *
     * @param server     JME {@link Server} where connection is added
     * @param connection added connection
     */
    @Override
    public void connectionAdded(Server server, HostedConnection connection) {
        LOGGER.debug("New connection : [ADDRESS]: {} | [CONNECTIONS COUNT]: {}",
                connection.getAddress(), server.getConnections().size());


        //GameServer gameServer = gameServersManager.findServer(1);

        //TODO: Replace this packet : Send information for the new server to the player
        //connection.send(new GameConnectionPacket(gameServer.getCommunicationPipe().getIP(), gameServer.getCommunicationPipe().getPort()));
    }

    /**
     * Called when a connection to a server is removed
     *
     * @param server server where connection is removed
     * @param conn   removed connection
     */
    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        LOGGER.debug("Connection removed! (" + conn.getAddress() + ")");
    }
}