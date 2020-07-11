package net.starype.client.core;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.client.util.TestingGeometries;
import net.starype.gd.client.physics.RigidBodyLinker;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.client.scene.TemporaryRawModelCalls;
import net.starype.gd.client.scene.Visualizer;
import net.starype.gd.physics.system.RigidBodyDynamicsHandler;
import net.starype.gd.physics.system.RigidBodySpaceManager;
import net.starype.gd.physics.component.RigidBodyDynamicsComponent;
import net.starype.gd.physics.component.RigidBodyComponent;

public class SimplePhysicsTest extends SimpleApplication {

    private EntityData entityData;
    private RigidBodySpaceManager spaceHandler;
    private EntityId boxEntity;

    public static void main(String[] args) {
        new SimplePhysicsTest();
    }

    private SimplePhysicsTest() {
        this.entityData = new DefaultEntityData();
        setSettings(new AppSettings(true));
        settings.setWidth(800);
        settings.setHeight(500);
        settings.setResizable(true);
        this.setShowSettings(false);
        this.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(20);
        addSystems(entityData);
        renderObjects(entityData);
    }

    @Override
    public void simpleUpdate(float tpf) {
        entityData.setComponent(boxEntity, new RigidBodyDynamicsComponent(new Vector3f(0, -2f, 0)));
    }

    private void addSystems(EntityData entityData) {
        BulletAppState bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);
        stateManager.attach(new Visualizer(rootNode, entityData));
        stateManager.attach(new RigidBodyLinker(entityData));
        spaceHandler = new RigidBodySpaceManager(entityData, bulletAppState);

        RigidBodyDynamicsHandler dynamicsHandler = new RigidBodyDynamicsHandler(entityData, bulletAppState);
        spaceHandler.enable();
        dynamicsHandler.enable();

    }

    private void renderObjects(EntityData entityData) {

        TemporaryRawModelCalls.createGameObjects(entityData, assetManager);
        TemporaryRawModelCalls.createLights(assetManager, rootNode, viewPort);
        addShapesForAllCurrent(entityData);
        boxEntity = spaceHandler.addBoxEntity(new Vector3f(0.5f,0.5f,0.5f), 80, new Vector3f(0, 15, 0));
        entityData.setComponent(boxEntity, new ShapeComponent(TestingGeometries.getCube(assetManager)));
        //BasicShapesFactory.createPhysicalCube(entityData, assetManager, new Vector3f(0, 10, 0));
    }

    private void addShapesForAllCurrent(EntityData entityData) {
        for(Entity entity : entityData.getEntities(ShapeComponent.class)) {
            Spatial shape = entity.get(ShapeComponent.class).getShape();
            entityData.setComponent(
                    entity.getId(),
                    new RigidBodyComponent(new RigidBodyControl(CollisionShapeFactory.createBoxShape(shape), 0)));
        }
    }
}
