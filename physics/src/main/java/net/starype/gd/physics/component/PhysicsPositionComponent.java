package net.starype.gd.physics.component;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class PhysicsPositionComponent extends InitialPositionComponent {

    public PhysicsPositionComponent(Vector3f location, Vector3f rotation) {
        super(location, rotation);
    }

    public PhysicsPositionComponent(Vector3f location, Quaternion rotation) {
        super(location, rotation);
    }
}
