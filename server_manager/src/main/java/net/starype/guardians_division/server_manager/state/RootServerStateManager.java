package net.starype.guardians_division.server_manager.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import net.starype.guardians_division.server_manager.state.action.IdleState;
import net.starype.guardians_division.server_manager.state.action.InitializationState;
import net.starype.guardians_division.server_manager.state.action.StoppingState;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RootServerStateManager extends BaseAppState {

    // List of available states (Defined in constructor)
    private final List<State> availableStates;
    // Currently working state
    private State currentState;


    public RootServerStateManager(AppStateManager manager) {
        this.availableStates = Arrays.asList(
                new State("Initialization", new InitializationState(manager)),
                new State("Idle", new IdleState()),
                new State("Stopping", new StoppingState(manager))
        );
    }

    /**
     * Sets the current state by the given name
     *
     * @param newStatusName name of the new state
     * @return whether there is a state named as provided.
     */
    public boolean setStatusByName(String newStatusName) {
        Optional<State> optionalStatus = getStatusByName(newStatusName);
        optionalStatus.ifPresent(this::setStatus);
        return optionalStatus.isPresent();
    }

    /**
     * @return the state linked to the provided name (if existing)
     */
    private Optional<State> getStatusByName(String name) {
        return availableStates.stream().filter(state ->
                state.getStateName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Sets the current state with the provided one
     * Launches state actions if possible
     */
    private void setStatus(State newState) {
        if (hasAnAvailableAction())
            currentState.getStateAction().actionEnded();
        currentState = newState;
        if (hasAnAvailableAction())
            currentState.getStateAction().actionInitialized();
    }

    /**
     * @return whether the current status can perform status actions.
     */
    private boolean hasAnAvailableAction() {
        return currentState != null && currentState.getStateAction() != null;
    }

    /**
     * Defines the default state as the first one (Initialization)
     */
    @Override
    protected void initialize(Application app) {
        setStatus(availableStates.get(0));
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
