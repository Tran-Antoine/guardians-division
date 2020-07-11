package net.starype.gd.physics.system;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import net.starype.gd.physics.component.RigidBodyComponent;
import net.starype.gd.physics.component.RigidBodyDynamicsComponent;

import java.util.Set;

public class RigidBodyDynamicsHandler extends DynamicsHandler {

    public RigidBodyDynamicsHandler(EntityData source, BulletAppState bulletAppState) {
        super(source, bulletAppState);
    }

    @Override
    protected void applyDynamics(Set<Entity> entities) {
        for(Entity entity : entities) {
            RigidBodyControl body = entity.get(RigidBodyComponent.class).getBody();
            RigidBodyDynamicsComponent dynamics = entity.get(RigidBodyDynamicsComponent.class);
            body.setLinearVelocity(dynamics.getLinearVelocity());
            body.setAngularVelocity(dynamics.getAngularVelocity());
            body.applyCentralForce(dynamics.getCentralForce());
        }
    }

    @Override
    protected EntitySet loadEntitySet(EntityData entityData) {
        return entityData.getEntities(RigidBodyComponent.class, RigidBodyDynamicsComponent.class);
    }
}
