package net.starype.gd.physics.system;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import java.util.Set;

public abstract class DynamicsHandler implements PhysicsTickListener {

    private EntitySet entities;
    private PhysicsSpace space;

    public DynamicsHandler(EntityData source, BulletAppState bulletAppState) {
        this.entities = loadEntitySet(source);
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

    protected abstract void applyDynamics(Set<Entity> entities);
    protected abstract EntitySet loadEntitySet(EntityData entityData);

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) { }
}
