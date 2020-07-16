package net.starype.gd.physics.system;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.*;
import net.starype.gd.physics.component.AddedToSpaceComponent;
import net.starype.gd.physics.component.PhysicsPositionComponent;
import net.starype.gd.physics.component.RigidBodyComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class SpaceManager<T extends EntityComponent> implements PhysicsTickListener {

    protected EntityData source;
    private EntitySet entities;

    protected Map<EntityId, PhysicsControl> idMap;
    private PhysicsSpace space;

    public SpaceManager(EntityData source, BulletAppState bulletState) {

        this.source = source;
        this.entities = source.getEntities(getBodyComponentType(), PhysicsPositionComponent.class);
        this.idMap = new HashMap<>();
        this.space = bulletState.getPhysicsSpace();
    }

    protected abstract Class<T> getBodyComponentType();
    protected abstract void setPosition(T component, Vector3f position);
    protected abstract void setRotation(T component, Quaternion rotation);

    protected abstract PhysicsControl getControlFrom(T component);

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
                new PhysicsPositionComponent(initialPosition, Quaternion.ZERO)); // TODO : add rotation
        return entity;
    }

    private void addToSpace(Set<Entity> addedEntities) {
        System.out.println("physics");
        for(Entity entity : addedEntities) {
            T component = entity.get(getBodyComponentType());
            PhysicsControl control = getControlFrom(component);
            space.add(control);
            PhysicsPositionComponent position = entity.get(PhysicsPositionComponent.class);
            System.out.println(position.getLocation());
            setPosition(component, position.getLocation());
            setRotation(component, position.getRotation());
            source.setComponent(entity.getId(), new AddedToSpaceComponent());
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
