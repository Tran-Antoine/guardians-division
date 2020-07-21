package net.starype.guardians_division.server_manager;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import net.starype.guardians_division.server_manager.util.CommunicationManager;
import net.starype.guardians_division.server_manager.util.DatabaseConnection;
import net.starype.guardians_division.server_manager.util.GameServersManager;
import net.starype.guardians_division.server_manager.util.Configuration;
import net.starype.guardians_division.server_manager.state.RootServerStateManager;

import java.util.logging.Level;

public class GuardiansDivisionRootServer extends SimpleApplication {

    private GuardiansDivisionRootServer() {
        super.start(JmeContext.Type.Headless);
    }

    public static void main(String... args) {
        // Disable JME3 logger
        java.util.logging.Logger.getLogger("com.jme3").setLevel(Level.OFF);
        new GuardiansDivisionRootServer();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new Configuration());
        stateManager.attach(new GameServersManager(stateManager));
        stateManager.attach(new CommunicationManager(stateManager));
        stateManager.attach(new DatabaseConnection(stateManager));
        // Has to be attached last
        stateManager.attach(new RootServerStateManager(stateManager));
    }

    @Override
    public void destroy() {
        stateManager.getState(RootServerStateManager.class).setStatusByName("Stopping");
        super.destroy();
    }
}