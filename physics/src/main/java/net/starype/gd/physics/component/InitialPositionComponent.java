package net.starype.gd.physics.component;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public abstract class InitialPositionComponent implements EntityComponent {

    private Vector3f location;
    private Quaternion rotation;

    public InitialPositionComponent(Vector3f location, Vector3f rotation) {
        this(location, new Quaternion().fromAngles(rotation.x, rotation.y, rotation.z));
    }

    public InitialPositionComponent(Vector3f location, Quaternion rotation) {
        this.location = location;
        this.rotation = rotation;
    }

    public Vector3f getLocation() {
        return location;
    }

    public Quaternion getRotation() {
        return rotation;
    }
}
