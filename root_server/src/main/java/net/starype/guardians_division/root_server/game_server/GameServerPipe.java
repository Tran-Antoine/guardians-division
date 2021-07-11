package net.starype.guardians_division.root_server.game_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class GameServerPipe {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServerPipe.class);

    /**
     * Server IP
     */
    private final InetAddress ip;
    /**
     * Server port
     */
    private final int port;

    /**
     * Linked {@link GameServer} to this pipe
     */
    private final GameServer gameServer;

    private Socket communicationSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public GameServerPipe(InetAddress ip, int port, GameServer gameServer) {
        this.ip = ip;
        this.port = port;
        this.gameServer = gameServer;
    }

    public void initSocket(Socket communicationSocket) {
        this.communicationSocket = communicationSocket;
        try {
            outputStream = new PrintWriter(communicationSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            outputStream.write("COMMUNICATION HELLO");
            LOGGER.debug("Communication initialized");
        }, "ServerReader").start();
    }

    /**
     * @return server IP
     */
    public InetAddress getIP() {
        return ip;
    }

    /**
     * @return server port
     */
    public int getPort() {
        return port;
    }
}
