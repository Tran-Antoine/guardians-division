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

public class ServerManagerConfiguration extends BaseAppState {

    /**
     * Server configuration file path constant
     */
    public static final String SERVER_CONFIGURATION_FILE_PATH = "serverConfiguration.json";

    /**
     * Server manager server listener port
     */
    private int gdPipePort;

    /**
     * GameServer Jar path
     */
    private String gameServerJarPath;

    /**
     * Server Manager port
     */
    private int serverManagerPort;

    /**
     * Minimal and maximal usable port values
     */
    private int gameServersMinPort, gameServersMaxPort;

    /**
     * Set of unusable port values
     */
    private Set<Integer> nonUsablePorts;

    public ServerManagerConfiguration() {
        loadConfiguration();
    }

    /**
     * Loads configuration file and sets variables value
     */
    private void loadConfiguration() {
        JsonObject object = null;
        try {
            URL url = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                    .getResource(SERVER_CONFIGURATION_FILE_PATH));
            object = new GsonBuilder().setPrettyPrinting().create()
                    .fromJson(new FileReader(url.getFile()), JsonObject.class);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        if (object == null) return;

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

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    protected void initialize(Application app) {
    }


    public int getServerManagerPort() {
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

    public int getGameServerListenerPort() {
        return gdPipePort;
    }

    public String getGameServerJarPath() {
        return gameServerJarPath;
    }
}
