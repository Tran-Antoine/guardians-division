package net.starype.guardians_division.server_manager.util;

import com.jme3.app.state.AppStateManager;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import net.starype.guardians_division.network.server_manager.IdentificationAnswerPacket;
import net.starype.guardians_division.network.server_manager.IdentificationPacket;
import net.starype.guardians_division.server_manager.networking.GDConnectionListener;
import net.starype.guardians_division.server_manager.networking.GDIdentificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayersServerRegisterer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayersServerRegisterer.class);

    /**
     * Registers existing packets
     */
    public static void registerPackets() {
        Serializer.registerClass(IdentificationPacket.class);
        Serializer.registerClass(IdentificationAnswerPacket.class);
    }

    /**
     * Loads server listeners
     */
    public static void loadListeners(AppStateManager appStateManager, Server server) {
        server.addConnectionListener(new GDConnectionListener(appStateManager));
        server.addMessageListener(new GDIdentificationListener(), IdentificationPacket.class);

        LOGGER.debug("Loaded server manager listeners");
    }
}

