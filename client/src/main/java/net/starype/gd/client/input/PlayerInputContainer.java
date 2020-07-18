package net.starype.gd.client.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class PlayerInputContainer implements ActionListener {

    private static final String[] MAPPINGS = {"FORWARD", "BACKWARD", "LEFT", "RIGHT", "JUMP"};
    private final InputManager inputManager;

    protected boolean left, right, forward, backward, jump;

    public PlayerInputContainer(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void initMappings() {
        inputManager.addMapping("FORWARD", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("BACKWARD", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("LEFT", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("RIGHT", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("JUMP", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, MAPPINGS);
    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {

        switch (name) {
            case "FORWARD": forward = !forward; break;
            case "BACKWARD": backward = !backward; break;
            case "LEFT": left = !left; break;
            case "RIGHT": right = !right; break;
            case "JUMP": jump = keyPressed;
        }
    }
}
