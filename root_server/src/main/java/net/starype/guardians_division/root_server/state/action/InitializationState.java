package net.starype.guardians_division.root_server.state.action;

import com.jme3.app.state.AppStateManager;
import net.starype.guardians_division.root_server.state.RootServerStateManager;
import net.starype.guardians_division.root_server.state.StateAction;
import net.starype.guardians_division.root_server.util.CommunicationManager;
import net.starype.guardians_division.root_server.util.Configuration;
import net.starype.guardians_division.root_server.util.PlayersServerRegisterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InitializationState implements StateAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationState.class);
    private final AppStateManager stateManager;


    public InitializationState(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void actionInitialized() {
        LOGGER.info("--- Initialization launched!");

        CommunicationManager manager = stateManager.getState(CommunicationManager.class);
        Configuration configuration = stateManager.getState(Configuration.class);

        PlayersServerRegisterer.registerPackets();
        LOGGER.debug("Registered root server packets");
        try {
            configuration.loadRootServerConfiguration();
            configuration.loadDatabaseConfiguration();
            LOGGER.debug("Loaded all JSON configurations successfully!");

            /*
            TODO: Remove when ORM system will be implemented
            stateManager.getState(DatabaseConnection.class).connect();
            LOGGER.debug("Connection to database successful!");
            */

            manager.loadGameServerCommunicationServer();
            LOGGER.debug("Socket server started !");
            manager.loadPlayerServer();
            LOGGER.debug("Root Server started!");
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Cannot start servers! Error: \n{}", e.getLocalizedMessage());
            LOGGER.error("Stopping application.");
            System.exit(1);
        }

        PlayersServerRegisterer.loadListeners(stateManager, manager.getRootServer());
        LOGGER.debug("Root server listeners loaded!");

        stateManager.getState(RootServerStateManager.class).setStatusByName("Idle");
    }

    @Override
    public void actionEnded() {
        LOGGER.info("--- Initialization finished!");
    }
}
