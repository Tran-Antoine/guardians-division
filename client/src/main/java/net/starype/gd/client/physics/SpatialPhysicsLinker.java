package net.starype.gd.client.physics;

import com.jme3.app.state.AbstractAppState;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.physics.component.AddedToSpaceComponent;

import java.util.Set;

public abstract class SpatialPhysicsLinker<T extends EntityComponent> extends AbstractAppState {

    private EntitySet entities;

    public SpatialPhysicsLinker(EntityData entityData) {
        entities = entityData.getEntities(getBodyComponentType(), ShapeComponent.class, AddedToSpaceComponent.class);
    }

    protected abstract Class<T> getBodyComponentType();
    protected abstract PhysicsControl getControlFrom(T component);

    @Override
    public void update(float tpf) {
        if(entities.applyChanges()) {
            link(entities.getAddedEntities());
            unlink(entities.getRemovedEntities());
            // todo: add for changed entities (if required)
        }
    }

    private void unlink(Set<Entity> removedEntities) {
        for(Entity entity : removedEntities) {
            T component = entity.get(getBodyComponentType());
            PhysicsControl control = getControlFrom(component);
            Spatial shape = entity.get(ShapeComponent.class).getShape();
            shape.removeControl(control);
        }
    }


    private void link(Set<Entity> addedEntities) {
        for(Entity entity : addedEntities) {
            T component = entity.get(getBodyComponentType());
            PhysicsControl control = getControlFrom(component);
            ShapeComponent shapeComponent = entity.get(ShapeComponent.class);
            Spatial shape = shapeComponent.getShape();
            System.out.println("linking: " + ((RigidBodyControl) control).getPhysicsLocation());
            shape.addControl(control);
            System.out.println("linked: " + ((RigidBodyControl) control).getPhysicsLocation());
        }
    }
}
