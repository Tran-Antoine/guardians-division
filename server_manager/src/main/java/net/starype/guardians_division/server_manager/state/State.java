package net.starype.guardians_division.server_manager.state;

public class State {

    private final StateAction stateAction;
    private final String stateName;

    public State(String stateName, StateAction action) {
        this.stateAction = action;
        this.stateName = stateName;
    }

    public StateAction getStateAction() {
        return stateAction;
    }

    public String getStateName() {
        return stateName;
    }
}
