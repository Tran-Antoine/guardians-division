package net.starype.gd.client.core;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;
import net.starype.gd.client.scene.FlatTerrain;
import net.starype.gd.client.scene.PositionComponent;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.client.scene.Visualizer;

public class GuardiansDivisionClient extends SimpleApplication {

    public static void main(String[] args) {
        new GuardiansDivisionClient();
    }

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

        changeCameraSettings();

        stateManager.attach(new Visualizer(rootNode, entityData.getEntities(ShapeComponent.class, PositionComponent.class)));

        FlatTerrain.create(entityData, assetManager);
    }

    private void changeCameraSettings() {

        flyCam.setMoveSpeed(40);
        cam.setLocation(new Vector3f(0, 30, 0));

        float fov = 70;
        float aspect = (float)cam.getWidth() / (float)cam.getHeight();
        cam.setFrustumNear(0.01f);
        cam.setFrustumPerspective(fov, aspect, cam.getFrustumNear(), cam.getFrustumFar());
    }
}
