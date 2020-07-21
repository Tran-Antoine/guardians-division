package net.starype.gd.client;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import net.starype.gd.client.networking.NetworkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Level;

public class GuardiansDivisionClientTest extends SimpleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuardiansDivisionClientTest.class);

    private GuardiansDivisionClientTest() {
        super.start(JmeContext.Type.Headless);
    }

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                new GuardiansDivisionClientTest();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void simpleInitApp() {
        NetworkClient networkClient = new NetworkClient();
        stateManager.attach(networkClient);
        try {
            LOGGER.debug("Connection to server manager...");
            networkClient.initClient();
            LOGGER.debug("Loaded correctly!");

        } catch (IOException exception) {
            LOGGER.error("Cannot connect to the server manager!");
            LOGGER.error(exception.getLocalizedMessage());
        }
        this.destroy();
    }
}
