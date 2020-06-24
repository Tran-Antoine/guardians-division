package net.starype.gd.client.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class FlatTerrain {

    private static final int LENGTH = 20;

    public static void create(EntityData source, AssetManager assetManager) {

        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("assets/carpet_0.jpg");
        tex.setWrap(WrapMode.Repeat);
        material.setTexture("ColorMap", tex);

        Mesh mesh = new Quad(LENGTH, LENGTH);
        mesh.scaleTextureCoordinates(new Vector2f(0.5f * LENGTH, 0.5f * LENGTH));

        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new PositionComponent(Vector3f.ZERO, new Vector3f(-FastMath.HALF_PI, 0, 0)),
                new ShapeComponent("terrain", mesh, material));
    }
}
