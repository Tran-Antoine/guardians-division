package net.starype.gd.physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import net.starype.gd.physics.component.InitialPositionComponent;
import net.starype.gd.physics.component.RigidBodyComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RigidBodySpaceManager implements PhysicsTickListener {

    private EntityData source;
    private EntitySet entities;

    private Map<EntityId, PhysicsControl> idMap;
    private PhysicsSpace space;

    public RigidBodySpaceManager(EntityData source, BulletAppState bulletState) {

        this.source = source;
        this.entities = source.getEntities(RigidBodyComponent.class, InitialPositionComponent.class);
        this.idMap = new HashMap<>();
        this.space = bulletState.getPhysicsSpace();
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
            addToSpace(entities.getAddedEntities());
            updateShape(entities.getChangedEntities());
            removeFromSpace(entities.getRemovedEntities());
        }
    }

    public EntityId addBoxEntity(Vector3f extent, float mass, Vector3f initialPosition) {
        return addEntity(new BoxCollisionShape(extent), mass, initialPosition);
    }

    public EntityId addSphereEntity(float radius, float mass, Vector3f initialPosition) {
        return addEntity(new SphereCollisionShape(radius), mass, initialPosition);
    }

    public EntityId addEntity(CollisionShape shape, float mass, Vector3f initialPosition) {
        RigidBodyControl body = new RigidBodyControl(shape, mass);
        EntityId entity = source.createEntity();
        idMap.put(entity, body);
        source.setComponents(entity,
                new RigidBodyComponent(body),
                new InitialPositionComponent(initialPosition, Vector3f.ZERO)); // TODO : add rotation
        return entity;
    }

    private void addToSpace(Set<Entity> addedEntities) {
        for(Entity entity : addedEntities) {
            RigidBodyControl control = entity.get(RigidBodyComponent.class).getBody();

            space.add(control);
            control.setPhysicsLocation(entity.get(InitialPositionComponent.class).getLocation());
        }
    }

    private void updateShape(Set<Entity> changedEntities) {
        for(Entity entity : changedEntities) {
            EntityId id = entity.getId();
            space.remove(idMap.remove(id)); // get rid of the current shape stored in the map

            PhysicsControl newControl = entity.get(RigidBodyComponent.class).getBody();
            idMap.put(id, newControl);
            space.add(newControl);
        }
    }

    private void removeFromSpace(Set<Entity> removedEntities) {
        for(Entity entity : removedEntities) {
            space.remove(idMap.remove(entity.getId()));
        }
    }


    @Override
    public void physicsTick(PhysicsSpace space, float tpf) { }
}
