package net.starype.gd.physics;

import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

import java.util.function.Consumer;

public class PhysicsComponent implements EntityComponent {

    private PhysicsControl control;
    private Vector3f initialPosition;
    private Consumer<Vector3f> placement;

    public  PhysicsComponent(PhysicsControl control, Vector3f initialPosition,
                            Consumer<Vector3f> placement) {
        this.control = control;
        this.initialPosition = initialPosition;
        this.placement = placement;
    }

    public PhysicsControl getControl() {
        return control;
    }

    public Vector3f getInitialPosition() {
        return initialPosition;
    }

    public Consumer<Vector3f> getPlacement() {
        return placement;
    }
}
