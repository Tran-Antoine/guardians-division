package net.starype.gd.client.core;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.gd.client.scene.FlatTerrain;
import net.starype.gd.client.scene.PositionComponent;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.client.scene.Visualizer;
import net.starype.gd.client.user.GDCamera;

public class GuardiansDivisionClient extends SimpleApplication {

    public static void main(String[] args) { new GuardiansDivisionClient(); }


    private EntityData entityData;

    private GuardiansDivisionClient() {

        entityData = new DefaultEntityData();

        super.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1500);
        settings.setHeight(900);
        super.setSettings(settings);
        super.start();
    }

    @Override
    public void simpleInitApp() {

        disableDefaults();
        setUpCamera();

        stateManager.attach(new Visualizer(rootNode, entityData.getEntities(ShapeComponent.class, PositionComponent.class)));

        FlatTerrain.create(entityData, assetManager);
    }

    private void disableDefaults() {
        // disables FlyByCamera and mappings, replacing them by GDCamera
        inputManager.clearMappings();
        guiNode.detachAllChildren();
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
    }

    private void setUpCamera() {
        GDCamera newCam = new GDCamera(this, cam);
        newCam.changeFOV(70);
        newCam.initMappings();
        cam.setLocation(new Vector3f(0, 10, 0));
    }
}
