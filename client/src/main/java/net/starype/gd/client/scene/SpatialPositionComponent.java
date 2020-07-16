package net.starype.gd.client.scene;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import net.starype.gd.physics.component.InitialPositionComponent;

public class SpatialPositionComponent extends InitialPositionComponent {

    public SpatialPositionComponent(Vector3f location, Vector3f rotation) {
        super(location, rotation);
    }

    public SpatialPositionComponent(Vector3f location, Quaternion rotation) {
        super(location, rotation);
    }
}
