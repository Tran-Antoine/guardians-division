package net.starype.guardians_division.server_manager.game_server;

import java.net.InetAddress;
import java.net.Socket;

public class GameServer {

    /** GameServerPipe */
    private GameServerPipe communicationPipe;
    /** Game data */
    private GameData gameData;
    /**
     * Server jar execution
     */
    private Process serverProcess;

    public GameServer(InetAddress ip, int port, int maxPlayersCount) {
        this.communicationPipe = new GameServerPipe(ip, port, this);
        gameData = new GameData(maxPlayersCount);
    }

    /**
     * Sets {@code serverProcess} variable with generated process
     */
    public void setProcess(Process serverProcess) {
        this.serverProcess = serverProcess;
    }

    /**
     * Defines communication socket
     *
     * @param socket {@link Socket} initialized by the game server
     */
    public void setCommunicationSocket(Socket socket) {
        communicationPipe.initSocket(socket);
    }

    /**
     * @return {@link GameServerPipe} linked to the server
     */
    public GameServerPipe getCommunicationPipe() {
        return communicationPipe;
    }

    /**
     * @return {@link GameData} linked to this server
     */
    public GameData getGameData() {
        return gameData;
    }
}
