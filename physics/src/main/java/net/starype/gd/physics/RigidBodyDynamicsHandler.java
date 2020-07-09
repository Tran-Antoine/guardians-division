package net.starype.gd.physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import net.starype.gd.physics.component.DynamicsComponent;
import net.starype.gd.physics.component.RigidBodyComponent;

import java.util.Set;

public class RigidBodyDynamicsHandler implements PhysicsTickListener {

    private EntitySet entities;
    private PhysicsSpace space;

    public RigidBodyDynamicsHandler(EntityData source, BulletAppState bulletAppState) {
        this.entities = source.getEntities(RigidBodyComponent.class, DynamicsComponent.class);
        this.space = bulletAppState.getPhysicsSpace();
    }

    public void enable() {
        space.addTickListener(this);
    }

    public void disable() {
        space.removeTickListener(this);
    }

    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        if(entities.applyChanges()) {
            applyDynamics(entities.getAddedEntities());
            applyDynamics(entities.getChangedEntities());
        }
    }

    private void applyDynamics(Set<Entity> entities) {
        for(Entity entity : entities) {
            RigidBodyControl body = entity.get(RigidBodyComponent.class).getBody();
            DynamicsComponent velComponent = entity.get(DynamicsComponent.class);
            body.setLinearVelocity(velComponent.getLinearVelocity());
            body.setAngularVelocity(velComponent.getAngularVelocity());
        }
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) { }

}
