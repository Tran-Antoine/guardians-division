package net.starype.gd.client.core.networking;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import com.jme3.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NetworkClient extends BaseAppState {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkClient.class);
    private Client client;

    public NetworkClient() {
        LOGGER.debug("PASSED HERE");
    }

    public void initClient() throws IOException {
        ClientRegisterer.registerPackets();
        client = Network.connectToServer("localhost", 34300);
        ClientRegisterer.loadListeners(client);
        client.start();
    }

    public Client getClient() {
        return client;
    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
        client.close();
    }
}
