package net.starype.gd.client.core.networking.listener;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import net.starype.guardians_division.network.server_manager.IdentificationAnswerPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.starype.guardians_division.network.server_manager.IdentificationAnswerPacket.Result;

import java.util.Optional;

public class IdentificationListener implements MessageListener<Client> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificationListener.class);

    @Override
    public void messageReceived(Client source, Message message) {
        IdentificationAnswerPacket packet = (IdentificationAnswerPacket) message;

        Optional<Result> result = Result.getResultById(packet.getResponse());
        result.ifPresent(res -> LOGGER.debug("Identification result: {}", res.name()));
    }
}
