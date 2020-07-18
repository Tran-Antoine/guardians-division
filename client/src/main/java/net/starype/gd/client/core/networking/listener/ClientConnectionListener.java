package net.starype.gd.client.core.networking.listener;

import com.jme3.network.*;
import net.starype.guardians_division.network.server_manager.IdentificationPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ClientConnectionListener implements ClientStateListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientConnectionListener.class);

    /**
     * Called when the specified client is fully connected to
     * the remote server.
     *
     * @param client
     */
    @Override
    public void clientConnected(Client client) {
        client.send(new IdentificationPacket(UUID.randomUUID().toString(), "`What a password!`"));
        LOGGER.debug("Identification packet sent");
    }

    /**
     * Called when the client has disconnected from the remote
     * server.  If info is null then the client shut down the
     * connection normally, otherwise the info object contains
     * additional information about the disconnect.
     *
     * @param client
     * @param info
     */
    @Override
    public void clientDisconnected(Client client, DisconnectInfo info) {

    }
}
