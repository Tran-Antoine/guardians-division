package net.starype.client.core;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.gd.client.scene.PositionComponent;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.client.scene.TemporaryRawModelCalls;
import net.starype.gd.client.scene.Visualizer;
import net.starype.gd.physics.RigidBodyDynamicsHandler;
import net.starype.gd.physics.RigidBodySpaceManager;
import net.starype.gd.physics.DynamicsComponent;

public class SimplePhysicsTest extends SimpleApplication {

    private EntityData entityData;
    private EntityId boxEntity;

    public static void main(String[] args) {
        new SimplePhysicsTest();
    }

    private SimplePhysicsTest() {
        this.entityData = new DefaultEntityData();
        this.setShowSettings(false);
        this.start();
    }

    @Override
    public void simpleInitApp() {

        BulletAppState bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);

        renderObjects(entityData);

        RigidBodySpaceManager spaceHandler = new RigidBodySpaceManager(entityData, bulletAppState);
        boxEntity = spaceHandler.addBoxEntity(new Vector3f(1,1,1), 80, new Vector3f(0, 10, 0));
        spaceHandler.enable();

        RigidBodyDynamicsHandler dynamicsHandler = new RigidBodyDynamicsHandler(entityData, bulletAppState);
        dynamicsHandler.enable();
    }

    @Override
    public void simpleUpdate(float tpf) {
        entityData.setComponent(boxEntity, new DynamicsComponent(new Vector3f(0, 10f, 0), Vector3f.ZERO));
    }

    private void renderObjects(EntityData entityData) {
        stateManager.attach(new Visualizer(
                rootNode,
                entityData.getEntities(ShapeComponent.class, PositionComponent.class)));

        TemporaryRawModelCalls.createGameObjects(entityData, assetManager);
        TemporaryRawModelCalls.createLights(assetManager, rootNode, viewPort);
    }
}
