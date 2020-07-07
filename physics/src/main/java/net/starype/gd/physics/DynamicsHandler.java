package net.starype.gd.physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import java.util.Set;

public class DynamicsHandler implements PhysicsTickListener {

    private EntityData source;
    private EntitySet entities;

    public DynamicsHandler(EntityData source, BulletAppState bulletAppState) {
        this.source = source;
        this.entities = source.getEntities(VelocityComponent.class);
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

        }
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) { }

}
