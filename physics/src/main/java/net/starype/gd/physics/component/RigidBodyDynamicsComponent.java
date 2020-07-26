package net.starype.gd.physics.component;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class RigidBodyDynamicsComponent implements EntityComponent {

    private Vector3f linearVelocity;
    private Vector3f angularVelocity;
    private Vector3f centralForce;

    public RigidBodyDynamicsComponent(Vector3f linearVelocity) {
        this(linearVelocity, Vector3f.ZERO);
    }

    public RigidBodyDynamicsComponent(Vector3f linearVelocity, Vector3f angularVelocity) {
        this(linearVelocity, angularVelocity, Vector3f.ZERO);
    }

    public RigidBodyDynamicsComponent(Vector3f linearVelocity, Vector3f angularVelocity, Vector3f centralForce) {
        this.linearVelocity = linearVelocity;
        this.angularVelocity = angularVelocity;
        this.centralForce = centralForce;
    }

    public Vector3f getLinearVelocity() {
        return linearVelocity;
    }

    public Vector3f getAngularVelocity() {
        return angularVelocity;
    }

    public Vector3f getCentralForce() {
        return centralForce;
    }
}
