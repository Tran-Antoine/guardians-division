package net.starype.gd.client.core;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.gd.client.scene.PositionComponent;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.client.scene.TemporaryRawModelCalls;
import net.starype.gd.client.scene.Visualizer;
import net.starype.gd.client.user.GDCamera;

public class GuardiansDivisionClient extends SimpleApplication {

    public static void main(String[] args) { new GuardiansDivisionClient(); }


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
    }

    private void attachStates() {
        stateManager.attach(new Visualizer(
                rootNode,
                entityData.getEntities(ShapeComponent.class, PositionComponent.class)));
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
