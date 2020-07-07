package net.starype.gd.client.physics;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.*;
import net.starype.gd.physics.BetterCharacterComponent;
import net.starype.gd.physics.RigidBodyComponent;

import java.util.Set;

public class SpatialPhysicsLinker extends AbstractAppState {

    private EntitySet entities;

    public SpatialPhysicsLinker(EntityData entityData) {
        entities = entityData.getEntities(new PhysicsControlFilter());
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

        }
    }
    
    private class PhysicsControlFilter implements ComponentFilter<EntityComponent> {

        @Override
        public Class<EntityComponent> getComponentType() {
            return EntityComponent.class;
        }

        @Override
        public boolean evaluate(EntityComponent c) {
            return c instanceof RigidBodyComponent || c instanceof BetterCharacterComponent;
        }
    }
}
