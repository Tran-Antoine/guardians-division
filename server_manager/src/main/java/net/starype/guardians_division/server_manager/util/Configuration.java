package net.starype.guardians_division.server_manager.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Configuration extends BaseAppState {

    /**
     * Root server configuration file path constant
     */
    private static final String SERVER_CONFIGURATION_FILE_PATH = "root_server.json";
    private static final String DATABASE_CONFIGURATION_FILE_PATH = "sql.json";

    // Root server configuration variables
    private int gdPipePort;
    private String gameServerJarPath;
    private int serverManagerPort;
    private int gameServersMinPort, gameServersMaxPort;
    private Set<Integer> nonUsablePorts;

    // Database configuration variables
    private String databaseIP;
    private String username;
    private String password;
    private String databaseName;

    /**
     * Loads configuration file and sets variables value
     */
    public void loadRootServerConfiguration() throws IOException, NullPointerException {
        URL url = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource(SERVER_CONFIGURATION_FILE_PATH));
        JsonObject object = new GsonBuilder().setPrettyPrinting().create()
                .fromJson(new FileReader(url.getFile()), JsonObject.class);

        serverManagerPort = object.get("serverManagerPort").getAsInt();
        gameServerJarPath = object.get("serverJarPath").getAsString();
        gdPipePort = object.get("socketListenerPort").getAsInt();

        JsonObject portsRange = object.getAsJsonObject("gameServersPortsRange");
        gameServersMinPort = portsRange.get("minimal").getAsInt();
        gameServersMaxPort = portsRange.get("maximal").getAsInt();
        JsonArray array = portsRange.get("nonUsable").getAsJsonArray();
        nonUsablePorts = new HashSet<>(array.size());
        array.iterator().forEachRemaining(element -> nonUsablePorts.add(element.getAsInt()));
    }

    public void loadDatabaseConfiguration() throws IOException, NullPointerException {
        URL url = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource(DATABASE_CONFIGURATION_FILE_PATH));
        JsonObject object = new GsonBuilder().setPrettyPrinting().create()
                .fromJson(new FileReader(url.getFile()), JsonObject.class);

        databaseIP = object.get("ip").getAsString();
        username = object.get("username").getAsString();
        password = object.get("password").getAsString();
        databaseName = object.get("database_name").getAsString();
    }

    // Root server configuration getters

    public int getRootManagerPort() {
        return serverManagerPort;
    }

    public int getGameServersMinimalPort() {
        return gameServersMinPort;
    }

    public int getGameServersMaximalPort() {
        return gameServersMaxPort;
    }

    public Set<Integer> getNonUsablePorts() {
        return nonUsablePorts;
    }

    public int getGameServerHandlerPort() {
        return gdPipePort;
    }

    public String getGameServerJarPath() {
        return gameServerJarPath;
    }

    // Database configuration getters

    public String getDatabaseIP(){
        return databaseIP;
    }

    public String getDatabaseUsername(){
        return username;
    }

    public String getDatabasePassword(){
        return password;
    }

    public String getDatabaseName(){
        return databaseName;
    }

    @Override
    protected void cleanup(Application app) {}
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    protected void initialize(Application app) {}
}
