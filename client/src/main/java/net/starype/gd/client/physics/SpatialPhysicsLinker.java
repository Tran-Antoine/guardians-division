package net.starype.gd.client.physics;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.*;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.physics.component.BodyComponent;

import java.util.Set;

public class SpatialPhysicsLinker extends AbstractAppState {

    private EntitySet entities;

    public SpatialPhysicsLinker(EntityData entityData) {
        entities = entityData.getEntities(BodyComponent.class, ShapeComponent.class);
    }

    @Override
    public void update(float tpf) {
        if(entities.applyChanges()) {
            link(entities.getAddedEntities());
            // todo: add for changed and removed entities
        }
    }

    private void link(Set<Entity> addedEntities) {
        for(Entity entity : addedEntities) {
            BodyComponent<?> bodyComponent = entity.get(BodyComponent.class);
            ShapeComponent shapeComponent = entity.get(ShapeComponent.class);
            shapeComponent.getShape().addControl(bodyComponent.getBody());
        }
    }
}
