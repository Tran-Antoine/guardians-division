package net.starype.gd.client.scene;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class PositionComponent implements EntityComponent {

    private Vector3f location;
    private Vector3f rotation;

    public PositionComponent(Vector3f location, Vector3f rotation) {
        this.location = location;
        this.rotation = rotation;
    }

    public Vector3f getLocation() {
        return location;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
