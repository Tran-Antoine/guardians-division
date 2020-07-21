package net.starype.gd.client.networking;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import com.jme3.network.Network;

import java.io.IOException;

public class NetworkClient extends BaseAppState {

    private Client client;

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
