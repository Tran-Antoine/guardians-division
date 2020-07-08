package net.starype.guardians_division.server_manager.util;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Server;
import net.starype.guardians_division.server_manager.networking.GDConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayersServerRegisterer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayersServerRegisterer.class);

    /**
     * Registers existing packets
     */
    public static void registerPackets() {
        // Has to be replaced:
        // Serializer.registerClass(GameConnectionPacket.class);
    }

    /**
     * Loads server listeners
     */
    public static void loadListeners(SimpleApplication main, Server server) {
        server.addConnectionListener(new GDConnectionListener(main));

        //server.addMessageListener(new MyClass(), StringMessage.class);

        LOGGER.debug("Loaded server manager listeners");
    }
}
