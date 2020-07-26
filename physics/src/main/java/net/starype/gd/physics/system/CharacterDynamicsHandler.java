package net.starype.gd.physics.system;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import net.starype.gd.physics.component.CharacterComponent;
import net.starype.gd.physics.component.CharacterDynamicsComponent;

import java.util.Set;

public class CharacterDynamicsHandler extends DynamicsHandler {

    public CharacterDynamicsHandler(EntityData source, BulletAppState bulletAppState) {
        super(source, bulletAppState);
    }

    @Override
    protected void applyDynamics(Set<Entity> entities) {
        for(Entity entity : entities) {
            BetterCharacterControl body = entity.get(CharacterComponent.class).getBody();
            CharacterDynamicsComponent dynamics = entity.get(CharacterDynamicsComponent.class);
            Vector3f warp = dynamics.getWarp();

            float angle = 0;//dynamics.getWalkingDirection().angleBetween(body.getWalkDirection());
            float speedFactor = body.isOnGround() ? 5 : 5 / (angle + 1);
            body.setWalkDirection(dynamics.getWalkingDirection().mult(speedFactor));
            if(dynamics.isJumpRequested()) { body.jump(); }
            if(warp != null) { body.warp(warp); }
        }
    }

    @Override
    protected EntitySet loadEntitySet(EntityData entityData) {
        return entityData.getEntities(CharacterComponent.class, CharacterDynamicsComponent.class);
    }
}
