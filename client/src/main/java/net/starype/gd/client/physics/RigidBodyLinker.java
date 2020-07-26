package net.starype.gd.client.physics;

import com.jme3.bullet.control.PhysicsControl;
import com.simsilica.es.EntityData;
import net.starype.gd.physics.component.RigidBodyComponent;

public class RigidBodyLinker extends SpatialPhysicsLinker<RigidBodyComponent> {

    public RigidBodyLinker(EntityData entityData) {
        super(entityData);
    }

    @Override
    protected Class<RigidBodyComponent> getBodyComponentType() {
        return RigidBodyComponent.class;
    }

    @Override
    protected PhysicsControl getControlFrom(RigidBodyComponent component) {
        return component.getBody();
    }
}
