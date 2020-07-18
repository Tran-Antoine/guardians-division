package net.starype.gd.client.core;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import net.starype.gd.client.core.networking.NetworkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GuardiansDivisionClient extends SimpleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuardiansDivisionClient.class);

    public static void main(String[] args) {
        new GuardiansDivisionClient();
    }

    private GuardiansDivisionClient() {
        super.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        NetworkClient networkClient = new NetworkClient();
        stateManager.attach(networkClient);
        try{
            LOGGER.debug("Connection to server manager...");
            networkClient.initClient();
            LOGGER.debug("Loaded correctly!");

        }catch (IOException exception){
            LOGGER.error("Cannot connect to the server manager!");
            LOGGER.error(exception.getLocalizedMessage());
        }
        this.destroy();
    }
}
