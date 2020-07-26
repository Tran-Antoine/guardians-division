package net.starype.gd.physics.component;

import com.jme3.bullet.control.RigidBodyControl;
import com.simsilica.es.EntityComponent;

public class RigidBodyComponent implements EntityComponent {

    private RigidBodyControl body;

    public RigidBodyComponent(RigidBodyControl control) {
        this.body = control;
    }

    public RigidBodyControl getBody() {
        return body;
    }
}
