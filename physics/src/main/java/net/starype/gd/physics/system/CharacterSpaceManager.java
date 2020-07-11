package net.starype.gd.physics.system;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import net.starype.gd.physics.component.CharacterComponent;

public class CharacterSpaceManager extends SpaceManager<CharacterComponent> {

    public CharacterSpaceManager(EntityData source, BulletAppState bulletState) {
        super(source, bulletState);
    }

    @Override
    protected Class<CharacterComponent> getBodyComponentType() {
        return CharacterComponent.class;
    }

    @Override
    protected void setPosition(CharacterComponent component, Vector3f position) {
        component.getBody().warp(position);
    }

    @Override
    protected PhysicsControl getControlFrom(CharacterComponent component) {
        return component.getBody();
    }
}
