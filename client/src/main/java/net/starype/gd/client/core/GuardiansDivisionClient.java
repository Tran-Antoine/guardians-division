package net.starype.gd.client.core;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.gd.client.input.InputComponentCreator;
import net.starype.gd.client.physics.CharacterLinker;
import net.starype.gd.client.physics.RigidBodyLinker;
import net.starype.gd.client.scene.TemporaryRawModelCalls;
import net.starype.gd.client.scene.Visualizer;
import net.starype.gd.client.user.GDCamera;
import net.starype.gd.physics.system.CharacterDynamicsHandler;
import net.starype.gd.physics.system.CharacterSpaceManager;
import net.starype.gd.physics.system.RigidBodyDynamicsHandler;
import net.starype.gd.physics.system.RigidBodySpaceManager;

public class GuardiansDivisionClient extends SimpleApplication {

    public static void main(String[] args) { new GuardiansDivisionClient(); }

    private Node camNode;
    private EntityData entityData;

    private GuardiansDivisionClient() {

        entityData = new DefaultEntityData();
        super.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1000);
        settings.setHeight(600);
        settings.setResizable(true);
        settings.setTitle("Guardians Division");
        super.setSettings(settings);
        super.start();
    }

    @Override
    public void simpleInitApp() {
        disableDefaults();
        attachStates();
        setUpCamera();
        TemporaryRawModelCalls.createGameObjects(entityData, assetManager);
        TemporaryRawModelCalls.createLights(assetManager, rootNode, viewPort);
        camNode = TemporaryRawModelCalls.createPlayer(entityData, stateManager.getState(InputComponentCreator.class));
        rootNode.attachChild(camNode);
    }

    @Override
    public void simpleUpdate(float tpf) {
        cam.setLocation(camNode.getWorldTranslation().add(0, 1, 0));
    }

    private void attachStates() {
        stateManager.attach(new Visualizer(rootNode, entityData));
        stateManager.attach(new RigidBodyLinker(entityData));
        stateManager.attach(new CharacterLinker(entityData));
        stateManager.attach(new InputComponentCreator(inputManager, cam));

        BulletAppState bullet = new BulletAppState();
        stateManager.attach(bullet);

        bullet.setDebugEnabled(true);
        new RigidBodySpaceManager(entityData, bullet).enable();
        new RigidBodyDynamicsHandler(entityData, bullet).enable();
        new CharacterSpaceManager(entityData, bullet).enable();
        new CharacterDynamicsHandler(entityData, bullet).enable();
    }

    private void disableDefaults() {
        // disables FlyByCamera and mappings, replacing them by GDCamera
        inputManager.clearMappings();
        guiNode.detachAllChildren();
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
    }

    private void setUpCamera() {
        GDCamera newCam = new GDCamera(inputManager, cam);
        newCam.changeFOV(100);
        newCam.setSensitivity(30);
        newCam.enable();
        cam.setLocation(new Vector3f(0, 5, 0));
    }
}
