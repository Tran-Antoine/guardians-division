package net.starype.client.util;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import net.starype.gd.client.scene.ShapeComponent;
import net.starype.gd.physics.component.InitialPositionComponent;
import net.starype.gd.physics.component.RigidBodyComponent;

public class BasicShapesFactory {

    public static void createPhysicalCube(EntityData entityData, AssetManager assetManager, Vector3f position) {
        Box box = new Box(0.5f, 0.5f, 0.5f);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        Spatial geometry = new Geometry("testing_cube", box);
        geometry.setMaterial(mat);
        RigidBodyControl control = new RigidBodyControl(
                new BoxCollisionShape(new Vector3f(box.xExtent, box.yExtent, box.zExtent)), 80);
        EntityId entity = entityData.createEntity();
        entityData.setComponents(entity,
                new InitialPositionComponent(position, Vector3f.ZERO),
                new ShapeComponent(geometry),
                new RigidBodyComponent(control));
    }
}
