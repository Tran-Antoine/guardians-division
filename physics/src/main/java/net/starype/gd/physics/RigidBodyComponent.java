package net.starype.gd.physics;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class RigidBodyComponent implements EntityComponent {

    private RigidBodyControl control;
    private Vector3f initialPosition;

    public RigidBodyComponent(RigidBodyControl control, Vector3f initialPosition) {
        this.control = control;
        this.initialPosition = initialPosition;
    }

    public RigidBodyControl getControl() {
        return control;
    }

    public Vector3f getInitialPosition() {
        return initialPosition;
    }
}
