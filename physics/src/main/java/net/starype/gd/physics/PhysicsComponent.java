package net.starype.gd.physics;

import com.jme3.bullet.control.PhysicsControl;
import com.simsilica.es.EntityComponent;

public class PhysicsComponent implements EntityComponent {

    private PhysicsControl control;

    public PhysicsComponent(PhysicsControl control) {
        this.control = control;
    }

    public PhysicsControl getControl() {
        return control;
    }
}
