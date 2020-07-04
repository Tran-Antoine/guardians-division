package net.starype.gd.client.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class GDModelBuilder {

    private AssetManager assetManager;
    private EntityData source;
    private float floorLength;

    public GDModelBuilder(AssetManager assetManager, EntityData source, float floorLength) {
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
                new ShapeComponent("surface", mesh, material));
    }

    public void createPillar(Vector2f position, float width, float height, String baseTexture, String topTexture) {
        float ratio = 0.98f;
        Vector3f worldPosition = new Vector3f(position.x, height/2, position.y);
        createBox(worldPosition, width, ratio * height, baseTexture, true);
        createBox(worldPosition.add(0, height, 0), width, (1 - ratio) * height, topTexture, false);
    }

    private void createBox(Vector3f position, float width, float height, String texture, boolean scale) {
        Mesh box = new Box(width, height, width);
        Texture tex = assetManager.loadTexture("assets/"+texture);
        tex.setWrap(WrapMode.Repeat);
        if(scale) box.scaleTextureCoordinates(new Vector2f(width, height));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", tex);

        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new PositionComponent(position, new Vector3f()),
                new ShapeComponent("pillar", box, mat));
    }

    public float floorLength() {
        return floorLength;
    }
}