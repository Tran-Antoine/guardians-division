package net.starype.gd.client.tool;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import net.starype.gd.client.scene.PositionComponent;
import net.starype.gd.client.scene.ShapeComponent;

public class BasicShapesFactory {

    public static void createCube(EntityData source, AssetManager assetManager, Vector3f position, ColorRGBA color) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new PositionComponent(position, Vector3f.ZERO),
                new ShapeComponent("terrain", new Box(1,1,1), material));

    }

    public static void createCube(EntityData source, AssetManager assetManager, Vector3f position, String textureName) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("assets/" + textureName);
        texture.setWrap(WrapMode.Repeat);
        material.setTexture("ColorMap", texture);
        Mesh box = new Box(1,1,1);
        box.scaleTextureCoordinates(new Vector2f(1,1));
        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new PositionComponent(position, Vector3f.ZERO),
                new ShapeComponent("terrain", box, material));

    }
}
