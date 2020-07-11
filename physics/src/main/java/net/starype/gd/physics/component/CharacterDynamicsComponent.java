package net.starype.gd.physics.component;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class CharacterDynamicsComponent implements EntityComponent {

    private Vector3f walkingDirection;
    private boolean jumpRequested;
    private Vector3f warp;

    public CharacterDynamicsComponent(Vector3f walkingDirection) {
        this(walkingDirection, false);
    }

    public CharacterDynamicsComponent(Vector3f walkingDirection, boolean jumpRequested) {
        this(walkingDirection, jumpRequested, null);
    }

    public CharacterDynamicsComponent(Vector3f walkingDirection, boolean jumpRequested, Vector3f warp) {
        this.walkingDirection = walkingDirection;
        this.jumpRequested = jumpRequested;
        this.warp = warp;
    }

    public Vector3f getWalkingDirection() {
        return walkingDirection;
    }

    public boolean isJumpRequested() {
        return jumpRequested;
    }

    public Vector3f getWarp() {
        return warp;
    }
}
