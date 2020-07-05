package net.starype.gd.physics;

import com.jme3.app.state.AbstractAppState;
import com.jme3.bullet.PhysicsSpace;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PhysicalEntityHandler extends AbstractAppState {

    private EntityData source;
    private EntitySet entities;

    private Map<EntityId, PhysicsControl> idMap;
    private PhysicsSpace space;

    public PhysicalEntityHandler(EntityData source, PhysicsSpace space) {
        this.source = source;
        this.entities = source.getEntities(PhysicsComponent.class);
        this.idMap = new HashMap<>();
        this.space = space;
    }

    public void addBoxEntity(Vector3f extent, float mass) {
        addEntity(new BoxCollisionShape(extent), mass);
    }

    public void addSphereEntity(float radius, float mass) {
        addEntity(new SphereCollisionShape(radius), mass);
    }

    private void addEntity(CollisionShape shape, float mass) {
        EntityId entity = source.createEntity();
        PhysicsControl body = new RigidBodyControl(shape, mass);
        idMap.put(entity, body);
        source.setComponent(entity, new PhysicsComponent(body));
    }

    @Override
    public void update(float tpf) {
        if(entities.applyChanges()) {
            addToSpace(entities.getAddedEntities());
            updateShape(entities.getChangedEntities());
            removeFromSpace(entities.getRemovedEntities());
        }
    }

    private void addToSpace(Set<Entity> addedEntities) {
        for(Entity entity : addedEntities) {
            space.add(idMap.get(entity.getId()));
        }
    }

    private void updateShape(Set<Entity> changedEntities) {
        for(Entity entity : changedEntities) {
            EntityId id = entity.getId();
            space.remove(idMap.get(id));
            PhysicsControl newControl = entity.get(PhysicsComponent.class).getControl();
            idMap.put(id, newControl);
            space.add(newControl);
        }
    }

    private void removeFromSpace(Set<Entity> removedEntities) {
        for(Entity entity : removedEntities) {
            space.remove(idMap.get(entity.getId()));
        }
    }
}
