package net.starype.gd.client.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class GDFlatModelBuilder {

    private AssetManager assetManager;
    private EntityData source;
    private float floorLength;

    public GDFlatModelBuilder(AssetManager assetManager, EntityData source, float floorLength) {
        this.assetManager = assetManager;
        this.source = source;
        this.floorLength = floorLength;
    }

    public void createTexturedFlatSurface(Vector3f position, Vector3f rotation, String textureName) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("assets/"+textureName);
        tex.setWrap(WrapMode.Repeat);
        material.setTexture("ColorMap", tex);

        Mesh mesh = new Quad(floorLength, floorLength);
        mesh.scaleTextureCoordinates(new Vector2f(0.25f * floorLength, 0.25f * floorLength));

        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new PositionComponent(position, rotation),
                new ShapeComponent("terrain", mesh, material));
    }

    public float floorLength() {
        return floorLength;
    }
}
