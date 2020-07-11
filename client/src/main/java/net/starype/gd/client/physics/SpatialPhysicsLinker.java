package net.starype.gd.client.physics;

import com.jme3.app.state.AbstractAppState;
import com.jme3.bullet.control.PhysicsControl;
import com.simsilica.es.*;
import net.starype.gd.client.scene.ShapeComponent;

import java.util.Set;

public abstract class SpatialPhysicsLinker<T extends EntityComponent> extends AbstractAppState {

    private EntitySet entities;

    public SpatialPhysicsLinker(EntityData entityData) {
        entities = entityData.getEntities(getBodyComponentType(), ShapeComponent.class);
    }

    protected abstract Class<T> getBodyComponentType();
    protected abstract PhysicsControl getControlFrom(T component);

    @Override
    public void update(float tpf) {
        if(entities.applyChanges()) {
            link(entities.getAddedEntities());
            // todo: add for changed and removed entities (if required)
        }
    }

    private void link(Set<Entity> addedEntities) {
        for(Entity entity : addedEntities) {
            PhysicsControl control = getControlFrom(entity.get(getBodyComponentType()));
            ShapeComponent shapeComponent = entity.get(ShapeComponent.class);
            shapeComponent.getShape().addControl(control);
        }
    }
}
