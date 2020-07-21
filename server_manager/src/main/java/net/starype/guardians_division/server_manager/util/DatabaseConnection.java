package net.starype.guardians_division.server_manager.util;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection extends BaseAppState {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    private final AppStateManager appStateManager;
    private Connection connection;

    public DatabaseConnection(AppStateManager appStateManager) {
        this.appStateManager = appStateManager;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        Configuration config = appStateManager.getState(Configuration.class);
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://" + config.getDatabaseIP() + "/" + config.getDatabaseName() + "?useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true";
        connection = DriverManager.getConnection(url, config.getDatabaseUsername(), config.getDatabasePassword());
    }

    public void disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            LOGGER.debug("Disconnection from database launched an error! {}", e.getLocalizedMessage());
        }
    }

    public boolean isConnected() {
        try {
            if ((connection == null) || (connection.isClosed()) || (!connection.isValid(5)))
                return false;
        } catch (SQLException e) {
            LOGGER.debug("Cannot check database connection state");
        }
        return true;
    }

    public void createAccount() {

    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
