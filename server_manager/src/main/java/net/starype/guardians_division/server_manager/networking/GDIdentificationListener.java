package net.starype.guardians_division.server_manager.networking;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import net.starype.guardians_division.network.server_manager.IdentificationAnswerPacket;
import net.starype.guardians_division.network.server_manager.IdentificationPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GDIdentificationListener implements MessageListener<HostedConnection> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GDIdentificationListener.class);

    @Override
    public void messageReceived(HostedConnection source, Message message) {
        IdentificationPacket packet = (IdentificationPacket) message;
        LOGGER.debug("Received a identification packet: UUID: {}, pass: {}", packet.getUniqueId(), packet.getPassword());
        //TODO: Check if player can connect (ban)
        source.send(new IdentificationAnswerPacket(IdentificationAnswerPacket.Result.ACCEPTED));
    }
}
