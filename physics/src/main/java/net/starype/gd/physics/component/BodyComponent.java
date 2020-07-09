package net.starype.gd.physics.component;

import com.jme3.bullet.control.PhysicsControl;
import com.simsilica.es.EntityComponent;

public class BodyComponent<T extends PhysicsControl> implements EntityComponent {

    private T body;

    public BodyComponent(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
