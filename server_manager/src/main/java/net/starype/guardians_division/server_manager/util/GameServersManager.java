package net.starype.guardians_division.server_manager.util;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import net.starype.guardians_division.server_manager.game_server.GameServer;
import net.starype.guardians_division.server_manager.game_server.GameServerPipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.stream.Collectors;

public class GameServersManager extends BaseAppState {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServersManager.class);
    private static final Random RANDOM = new Random();

    private final ServerManagerConfiguration configuration;
    /**
     * Machine IP
     */
    private final String machineIP;
    /**
     * Server list
     */
    private Set<GameServer> servers;

    public GameServersManager(SimpleApplication main, String machineIP) {
        servers = new HashSet<>();
        this.machineIP = machineIP;
        this.configuration = main.getStateManager().getState(ServerManagerConfiguration.class);
    }

    /**
     * @return an available port to host a server
     */
    private int findAvailablePort() {
        int minimalPort = configuration.getGameServersMinimalPort();
        int maximalPort = configuration.getGameServersMaximalPort();
        Set<Integer> usedPorts = servers.stream().map(GameServer::getCommunicationPipe)
                .map(GameServerPipe::getPort).collect(Collectors.toSet());

        int chosen;
        do {
            chosen = RANDOM.nextInt(maximalPort - minimalPort) + minimalPort;
        } while (usedPorts.contains(chosen) || configuration.getNonUsablePorts().contains(chosen));

        LOGGER.debug("Chosen port: " + chosen);
        return chosen;
    }


    /**
     * @param neededSlots How much players shall connect
     * @return {@link GameServer} depending on how much players shall connect to
     * Returns an existing server if possible or creates one
     */
    public GameServer findServer(int neededSlots) {
        Set<GameServer> available = servers.stream().filter(gameServer -> gameServer.getGameData().getRemainingSlots() >= neededSlots)
                .collect(Collectors.toSet());
        if (available.size() > 0) {
            return available.iterator().next();
        } else {
            try {
                int port = findAvailablePort();
                InetAddress address = InetAddress.getByName(machineIP);
                //TODO : Change max players

                GameServer newServer = new GameServer(address, port, 4);
                servers.add(newServer);
                new Thread(() -> launchServerJar(newServer), "ServerStarter").start();
                return newServer;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            //TODO: Change exception type
        }
        return null;
    }

    /**
     * Launches a game server jar providing port
     *
     * @param server {@link GameServer} instance to get chosen port and set its process variable
     */
    private void launchServerJar(GameServer server) {
        String path = configuration.getState(ServerManagerConfiguration.class).getGameServerJarPath();
        try {
            server.setProcess(Runtime.getRuntime().exec(String.format("java -jar %s %s %s", path, server.getCommunicationPipe().getPort(),
                    server.getGameData().getMaxPlayers())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param address Server address
     * @param port    Server port
     * @return Optional of GameServer found by address and port
     */
    public Optional<GameServer> getServer(SocketAddress address, int port) {
        return servers.stream().filter(gameServer -> gameServer.getCommunicationPipe().getIP().equals(address)
                && gameServer.getCommunicationPipe().getPort() == port).findFirst();
    }

    /**
     * @return GameServer instances
     */
    public Set<GameServer> getServers() {
        return Collections.unmodifiableSet(servers);
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