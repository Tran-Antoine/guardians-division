package net.starype.guardians_division.root_server.state.action;

import com.jme3.app.state.AppStateManager;
import net.starype.guardians_division.root_server.util.CommunicationManager;
import net.starype.guardians_division.root_server.state.StateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoppingState implements StateAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoppingState.class);
    private final AppStateManager stateManager;


    public StoppingState(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void actionInitialized() {
        LOGGER.debug("Stopping server manager!");
    }

    @Override
    public void actionEnded() {
        CommunicationManager manager = stateManager.getState(CommunicationManager.class);
        manager.getRootServer().close();
        manager.getGameServerHandler().close();

        // TODO: Save existing game servers to handle them after reboot.
        //getStateManager().getState(GameServersManager.class).getServers().forEach(server -> server.getServer().stopServer());
    }
}
