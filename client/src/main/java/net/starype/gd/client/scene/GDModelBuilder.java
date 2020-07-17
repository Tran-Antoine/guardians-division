package net.starype.gd.client.scene;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import net.starype.gd.physics.component.PhysicsPositionComponent;
import net.starype.gd.physics.component.RigidBodyComponent;

public class GDModelBuilder {

    public static final Vector3f BOTTOM_ROTATION = new Vector3f(-FastMath.HALF_PI, 0, 0);
    public static final Vector3f TOP_ROTATION    = new Vector3f(FastMath.HALF_PI, 0, 0);
    public static final Vector3f BACK_ROTATION   = new Vector3f();
    public static final Vector3f LEFT_ROTATION   = new Vector3f(0, -FastMath.HALF_PI, 0);
    public static final Vector3f RIGHT_ROTATION  = new Vector3f(0, FastMath.HALF_PI, 0);
    public static final Vector3f FRONT_ROTATION  = new Vector3f(0, FastMath.PI, 0);


    private AssetManager assetManager;
    private EntityData source;
    private float floorLength;

    public GDModelBuilder(AssetManager assetManager, EntityData source, float floorLength) {
        this.assetManager = assetManager;
        this.source = source;
        this.floorLength = floorLength;
    }

    public void createTexturedPlane(Vector3f position, Vector3f rotation, String textureName) {

        CollisionShape boxShape = new BoxCollisionShape(new Vector3f(floorLength/2, floorLength/2, 1));
        RigidBodyControl control = new RigidBodyControl(boxShape, 0);
        Spatial plane = createPlaneSpatial(textureName);

        Node parent = new Node();
        parent.attachChild(plane);
        plane.setLocalTranslation(-floorLength/2, -floorLength/2, 1f);

        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new SpatialPositionComponent(position, rotation),
                new PhysicsPositionComponent(position, rotation),
                new SpatialComponent(parent),
                new RigidBodyComponent(control));
    }

    private Spatial createPlaneSpatial(String textureName) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("assets/"+textureName);
        tex.setWrap(WrapMode.Repeat);
        material.setTexture("ColorMap", tex);
        material.setColor("Color", ColorRGBA.Cyan);

        Mesh mesh = new Quad(floorLength, floorLength);
        mesh.scaleTextureCoordinates(new Vector2f(0.5f * floorLength, 0.5f * floorLength));
        return asSpatial("plane", mesh, material);
    }

    public void createPillar(Vector2f position, float width, float height, String baseTexture, String topTexture) {

        float ratio = 0.98f;
        float baseHeight = ratio * height;
        float topHeight = height - baseHeight;

        Spatial base = createBox(width, ratio * height, baseTexture, true);
        Spatial top = createBox(width, (1 - ratio) * height, topTexture, false);
        Node group = new Node();
        Vector3f center = new Vector3f(position.x, height / 2, position.y);
        BoxCollisionShape collisionShape = new BoxCollisionShape(new Vector3f(width/2, height/2, width/2));
        group.attachChild(base);
        group.attachChild(top);
        base.setLocalTranslation(0, -topHeight / 2, 0);
        top.setLocalTranslation(0, baseHeight / 2, 0);
        EntityId entity = source.createEntity();
        source.setComponents(entity,
                new SpatialComponent(group),
                new SpatialPositionComponent(center, Quaternion.ZERO),
                new PhysicsPositionComponent(center, Quaternion.ZERO),
                new RigidBodyComponent(new RigidBodyControl(collisionShape, 0)));
    }

    private Spatial createBox(float width, float height, String texture, boolean scale) {
        Mesh box = new Box(width/2, height/2, width/2);
        Texture tex = assetManager.loadTexture("assets/"+texture);
        tex.setWrap(WrapMode.Repeat);
        if(scale) box.scaleTextureCoordinates(new Vector2f(width, height));
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setTexture("DiffuseMap", tex);
        /*EntityId entity = source.createEntity();
        source.setComponents(entity,
                new SpatialPositionComponent(position, Quaternion.ZERO),
                new SpatialComponent());*/
        return asSpatial("pillar", box, mat);
    }

    private Spatial asSpatial(String name, Mesh shape, Material material) {
        Spatial geometry = new Geometry(name, shape);
        geometry.setMaterial(material);
        return geometry;
    }

    public float floorLength() {
        return floorLength;
    }
}
