package net.starype.guardians_division.server_manager.state.action;

import net.starype.guardians_division.server_manager.state.StateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdleState implements StateAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdleState.class);

    public IdleState() {
    }

    @Override
    public void actionInitialized() {
        LOGGER.info("--- State: Idle");
        LOGGER.debug("Waiting for players...");
    }

    @Override
    public void actionEnded() {
    }
}
