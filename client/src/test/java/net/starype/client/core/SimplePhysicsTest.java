package net.starype.client.core;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.gd.client.input.InputComponentCreator;
import net.starype.gd.client.physics.CharacterLinker;
import net.starype.gd.client.physics.RigidBodyLinker;
import net.starype.gd.client.scene.SpatialComponent;
import net.starype.gd.client.scene.TemporaryRawModelCalls;
import net.starype.gd.client.scene.Visualizer;
import net.starype.gd.physics.component.CharacterComponent;
import net.starype.gd.physics.component.PhysicsPositionComponent;
import net.starype.gd.physics.component.RigidBodyComponent;
import net.starype.gd.physics.system.CharacterDynamicsHandler;
import net.starype.gd.physics.system.CharacterSpaceManager;
import net.starype.gd.physics.system.RigidBodyDynamicsHandler;
import net.starype.gd.physics.system.RigidBodySpaceManager;

public class SimplePhysicsTest extends SimpleApplication {

    private EntityData entityData;
    private RigidBodySpaceManager rigidSpace;
    private Node camNode;

    public static void main(String[] args) {
        new SimplePhysicsTest();
    }

    private SimplePhysicsTest() {
        this.entityData = new DefaultEntityData();
        this.camNode = new Node();
        setSettings(new AppSettings(true));
        settings.setWidth(900);
        settings.setHeight(500);
        settings.setResizable(true);
        this.setShowSettings(false);
        this.start();
    }

    @Override
    public void simpleInitApp() {
        addSystems(entityData);
        renderObjects(entityData);
        cam.setFrustumPerspective(70, (float) cam.getWidth() / cam.getHeight(), 0.001f, 100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        cam.setLocation(camNode.getWorldTranslation().add(0, 0.5f, 0));
    }

    private void addSystems(EntityData entityData) {
        BulletAppState bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(false);
        stateManager.attach(bulletAppState);
        stateManager.attach(new Visualizer(rootNode, entityData));
        stateManager.attach(new RigidBodyLinker(entityData));
        stateManager.attach(new CharacterLinker(entityData));
        stateManager.attach(new InputComponentCreator(inputManager, cam));


        rigidSpace = new RigidBodySpaceManager(entityData, bulletAppState);
        CharacterSpaceManager characterSpace = new CharacterSpaceManager(entityData, bulletAppState);

        RigidBodyDynamicsHandler rigidDynamics = new RigidBodyDynamicsHandler(entityData, bulletAppState);
        CharacterDynamicsHandler characterDynamics = new CharacterDynamicsHandler(entityData, bulletAppState);
        rigidSpace.enable();
        rigidDynamics.enable();
        characterSpace.enable();
        characterDynamics.enable();
    }

    private void renderObjects(EntityData entityData) {

        TemporaryRawModelCalls.createGameObjects(entityData, assetManager);
        TemporaryRawModelCalls.createLights(assetManager, rootNode, viewPort);
        addShapesForAllCurrent(entityData);

        //boxEntity = rigidSpace.addBoxEntity(new Vector3f(0.5f, 0.5f, 0.5f), 80, new Vector3f(0, 15, 0));
        //entityData.setComponent(boxEntity, new SpatialComponent(TestingGeometries.getCube(assetManager)));
        rigidSpace.addBoxEntity(new Vector3f(20, 1, 20), 0, new Vector3f(0, 0, 0));
        BetterCharacterControl playerControl = new BetterCharacterControl(0.3f, 1.5f, 80);
        playerControl.setJumpForce(new Vector3f(0, 150, 0));
        EntityId playerEntity = entityData.createEntity();
        entityData.setComponents(playerEntity,
                new CharacterComponent(playerControl),
                new PhysicsPositionComponent(new Vector3f(0, 3, 0), Vector3f.ZERO));

        InputComponentCreator inputCreator = stateManager.getState(InputComponentCreator.class);
        inputCreator.setEntityData(entityData, playerEntity);
        inputCreator.initListener();

        camNode.addControl(playerControl);
        rootNode.attachChild(camNode);
    }

    private void addShapesForAllCurrent(EntityData entityData) {
        for (Entity entity : entityData.getEntities(SpatialComponent.class)) {
            Spatial shape = entity.get(SpatialComponent.class).getShape();
            entityData.setComponent(
                    entity.getId(),
                    new RigidBodyComponent(new RigidBodyControl(CollisionShapeFactory.createBoxShape(shape), 0)));
        }
    }
}
