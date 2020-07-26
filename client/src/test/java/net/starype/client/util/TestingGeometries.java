package net.starype.client.util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class TestingGeometries {

    public static Geometry getCube(AssetManager assetManager) {
        Geometry box = new Geometry("box", new Box(0.5f, 0.5f, 0.5f));
        box.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        return box;
    }
}
