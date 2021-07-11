package net.starype.guardians_division.root_server.game_server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameData {

    /** Game has started */
    private boolean gameStarted = false;

    /** Max server players count */
    private final int maxPlayers;

    //TODO: Change to GuardiansDivisionPlayer objects
    /** Steam players */
    private Set<Object> players;

    public GameData(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        players = new HashSet<>();
    }

    /**
     * Set the game started
     */
    public void startGame(){
        gameStarted = true;
    }

    /**
     * @return the max players count in the server
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * @return an unmodifiable set of players
     */
    public Set<Object> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    /**
     * @return remaining slots in server
     */
    public int getRemainingSlots(){
        return maxPlayers - players.size();
    }
}
