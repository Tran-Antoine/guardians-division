package net.starype.gd.client.scene;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import net.starype.gd.client.input.InputComponentCreator;
import net.starype.gd.client.util.MultiDimensionalIterator;
import net.starype.gd.physics.component.CharacterComponent;
import net.starype.gd.physics.component.PhysicsPositionComponent;

import java.util.List;
import java.util.Random;

/**
 * A Class that is temporary, since because of it, it's currently the client who decides how the map is made,
 * even though this is something that should be decided by the server itself, then executed by the client
 */
public class TemporaryRawModelCalls {

    private static final float FLOOR_LENGTH = 40;
    private static final float PILLARS_COUNT_SQRT = 10;
    private static final float SPREADING_FACTOR = 0.8f;
    private static final float MINIMAL_PILLAR_WIDTH = 0.8f;
    private static final float MINIMAL_PILLAR_HEIGHT = 0.8f;

    public static void createGameObjects(EntityData entityData, AssetManager assetManager) {

        GDModelBuilder builder = new GDModelBuilder(assetManager, entityData, FLOOR_LENGTH);

        float corner = builder.floorLength() / 2 + 1;

        String floorTextureName = "carpet_1.jpg";

        builder.createTexturedPlane(
                new Vector3f(0,-1, 0),
                GDModelBuilder.BOTTOM_ROTATION,
                floorTextureName
        );

        builder.createTexturedPlane(
                new Vector3f(0, 2*corner - 1, 0),
                GDModelBuilder.TOP_ROTATION,
                floorTextureName
        );

        String wallTextureName = "carpet_0.jpg";
        builder.createTexturedPlane(
                new Vector3f(0, corner - 1, -corner),
                GDModelBuilder.BACK_ROTATION,
                wallTextureName);

        builder.createTexturedPlane(
                new Vector3f(corner, corner - 1, 0),
                GDModelBuilder.LEFT_ROTATION,
                wallTextureName);

        builder.createTexturedPlane(
                new Vector3f(-corner, corner - 1, 0),
                GDModelBuilder.RIGHT_ROTATION,
                wallTextureName);

        builder.createTexturedPlane(
                new Vector3f(0, corner - 1, corner),
                GDModelBuilder.FRONT_ROTATION,
                wallTextureName);

        String base = "carpet_2.jpg";
        String top = "carpet_3.jpg";

        createRandomPillars(base, top, builder.floorLength(), builder);
    }

    private static void createRandomPillars(String base, String top, float floorLength, GDModelBuilder builder) {
        MultiDimensionalIterator scanner = new MultiDimensionalIterator();

        float margin = floorLength / (2*PILLARS_COUNT_SQRT);
        float start = -floorLength/2 + margin;
        scanner.queueIteration(start, -start, floorLength / PILLARS_COUNT_SQRT, 2);
        scanner.addAction((values) -> createRandomPillar(
                values,
                margin * SPREADING_FACTOR,
                builder,
                base,
                top));
        scanner.run();
    }

    private static void createRandomPillar(List<Float> doubles, float randomMargin,
                                           GDModelBuilder builder, String base, String top) {
        Vector2f center = toVector(doubles);
        Random random = new Random();
        center.addLocal(
                (random.nextFloat() * 2 * randomMargin) - randomMargin,
                (random.nextFloat() * 2 * randomMargin) - randomMargin);
        float width = 1.4f * random.nextFloat() + MINIMAL_PILLAR_WIDTH;
        float height = (float) Math.pow(4, 2 * random.nextFloat()) - 1 + MINIMAL_PILLAR_HEIGHT;
        builder.createPillar(center, width, height, base, top);
    }

    private static Vector2f toVector(List<Float> values) {
        return new Vector2f(
                values.get(0),
                values.get(1)
        );
    }

    public static void createLights(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        AmbientLight al = new AmbientLight();
        al.setColor(new ColorRGBA(0.91f, 0.91f, 0.791f, 1));

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        SSAOFilter ssaoFilter = new SSAOFilter(4, 20f, 0.35f, 0.61f);
        fpp.addFilter(ssaoFilter);
        viewPort.addProcessor(fpp);

        rootNode.addLight(al);
        rootNode.addLight(dl);
    }

    public static Node createPlayer(EntityData entityData, InputComponentCreator input) {
        BetterCharacterControl playerControl = new BetterCharacterControl(0.3f, 1.5f, 80);
        playerControl.setJumpForce(new Vector3f(0, 300, 0));

        EntityId entity = entityData.createEntity();
        entityData.setComponents(entity,
                new CharacterComponent(playerControl),
                new PhysicsPositionComponent(new Vector3f(0, 18, 0), Quaternion.ZERO));

        input.setEntityData(entityData, entity);
        input.initListener();

        Node camNode = new Node();
        camNode.addControl(playerControl);

        return camNode;
    }
}
