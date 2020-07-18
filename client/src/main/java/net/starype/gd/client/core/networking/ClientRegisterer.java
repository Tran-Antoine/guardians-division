package net.starype.gd.client.core.networking;

import com.jme3.network.Client;
import com.jme3.network.serializing.Serializer;
import net.starype.gd.client.core.networking.listener.ClientConnectionListener;
import net.starype.gd.client.core.networking.listener.IdentificationListener;
import net.starype.guardians_division.network.server_manager.IdentificationAnswerPacket;
import net.starype.guardians_division.network.server_manager.IdentificationPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientRegisterer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRegisterer.class);

    /**
     * Loads packets
     */
    public static void registerPackets(){
        Serializer.registerClass(IdentificationPacket.class);
        Serializer.registerClass(IdentificationAnswerPacket.class);
    }

    /**
     * Loads client listeners
     */
    public static void loadListeners(Client client) {
        client.addMessageListener(new IdentificationListener(), IdentificationAnswerPacket.class);
        client.addClientStateListener(new ClientConnectionListener());
        LOGGER.debug("Loaded client listeners");
    }
}
