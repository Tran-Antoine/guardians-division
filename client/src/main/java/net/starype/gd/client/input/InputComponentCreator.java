package net.starype.gd.client.input;

import com.jme3.app.state.AbstractAppState;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import net.starype.gd.physics.component.CharacterDynamicsComponent;

public class InputComponentCreator extends AbstractAppState {

    private PlayerInputContainer inputListener;
    private EntityData entityData;
    private EntityId playerID;
    private Camera camera;

    private final Vector3f walkDirection = new Vector3f();

    public InputComponentCreator(InputManager inputManager, Camera camera) {
        this(inputManager, null, null, camera);
    }

    public InputComponentCreator(InputManager inputManager, EntityData entityData, EntityId playerID, Camera camera) {
        this.inputListener = new PlayerInputContainer(inputManager);
        this.entityData = entityData;
        this.playerID = playerID;
        this.camera = camera;
    }

    public void setEntityData(EntityData entityData, EntityId playerID) {
        this.entityData = entityData;
        this.playerID = playerID;
    }

    @Override
    public void update(float tpf) {

        Vector3f camForward = camera.getDirection();
        Vector3f camLeft = camera.getLeft();
        walkDirection.set(0, 0, 0);

        if (inputListener.left) {
            walkDirection.addLocal(new Vector3f(camLeft.x, 0, camLeft.z));
        }
        if (inputListener.right) {
            walkDirection.addLocal(new Vector3f(-camLeft.x, 0, -camLeft.z));
        }
        if (inputListener.forward) {
            walkDirection.addLocal(new Vector3f(camForward.x, 0, camForward.z));
        }
        if (inputListener.backward) {
            walkDirection.addLocal(new Vector3f(-camForward.x, 0, -camForward.z));
        }

        entityData.setComponent(playerID, new CharacterDynamicsComponent(walkDirection, inputListener.jump));
    }

    public void initListener() {
        inputListener.initMappings();
    }
}

