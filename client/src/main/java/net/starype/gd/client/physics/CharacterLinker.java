package net.starype.gd.client.physics;

import com.jme3.bullet.control.PhysicsControl;
import com.simsilica.es.EntityData;
import net.starype.gd.physics.component.CharacterComponent;

public class CharacterLinker extends SpatialPhysicsLinker<CharacterComponent> {

    public CharacterLinker(EntityData entityData) {
        super(entityData);
    }

    @Override
    protected Class<CharacterComponent> getBodyComponentType() {
        return CharacterComponent.class;
    }

    @Override
    protected PhysicsControl getControlFrom(CharacterComponent component) {
        return component.getBody();
    }
}
