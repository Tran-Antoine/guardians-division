package net.starype.guardians_division.root_server;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import net.starype.guardians_division.root_server.state.RootServerStateManager;
import net.starype.guardians_division.root_server.util.CommunicationManager;
import net.starype.guardians_division.root_server.util.Configuration;
import net.starype.guardians_division.root_server.util.GameServersManager;

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
        // TODO: Remove the DatabaseConnection class after ORM system is implemented
        // stateManager.attach(new DatabaseConnection(stateManager));
        // Has to be attached last
        stateManager.attach(new RootServerStateManager(stateManager));
    }

    @Override
    public void destroy() {
        stateManager.getState(RootServerStateManager.class).setStatusByName("Stopping");
        super.destroy();
    }
}