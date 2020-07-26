package net.starype.gd.physics.system;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import net.starype.gd.physics.component.RigidBodyComponent;

public class RigidBodySpaceManager extends SpaceManager<RigidBodyComponent> {

    public RigidBodySpaceManager(EntityData source, BulletAppState bulletState) {
        super(source, bulletState);
    }

    @Override
    protected Class<RigidBodyComponent> getBodyComponentType() {
        return RigidBodyComponent.class;
    }

    @Override
    protected void setPosition(RigidBodyComponent component, Vector3f position) {
        component.getBody().setPhysicsLocation(position);
    }

    @Override
    protected void setRotation(RigidBodyComponent component, Quaternion rotation) {
        component.getBody().setPhysicsRotation(rotation);
    }

    @Override
    protected PhysicsControl getControlFrom(RigidBodyComponent component) {
        return component.getBody();
    }

    public EntityId addBoxEntity(Vector3f extent, float mass, Vector3f initialPosition) {
        return addEntity(new BoxCollisionShape(extent), mass, initialPosition);
    }

    public EntityId addSphereEntity(float radius, float mass, Vector3f initialPosition) {
        return addEntity(new SphereCollisionShape(radius), mass, initialPosition);
    }
}
