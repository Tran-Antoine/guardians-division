package net.starype.gd.physics;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class DynamicsComponent implements EntityComponent {

    private Vector3f linearVelocity;
    private Vector3f angularVelocity;

    public DynamicsComponent(Vector3f linearVelocity, Vector3f angularVelocity) {
        this.linearVelocity = linearVelocity;
        this.angularVelocity = angularVelocity;
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }

    public Vector3f getAngularVelocity() {
        return angularVelocity;
    }
}
