package net.starype.gd.client.scene;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import net.starype.gd.client.util.MultiDimensionalIterator;

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
    private static final float MINIMAL_PILLAR_WIDTH = 0.4f;
    private static final float MINIMAL_PILLAR_HEIGHT = 0.4f;

    public static void createGameObjects(EntityData entityData, AssetManager assetManager) {

        GDModelBuilder builder = new GDModelBuilder(assetManager, entityData, FLOOR_LENGTH);

        float corner = builder.floorLength() / 2;

        builder.createTexturedFlatSurface(
                new Vector3f(-corner,0, corner),
                new Vector3f(-FastMath.HALF_PI, 0, 0),
                "carpet_1.jpg");

        builder.createTexturedFlatSurface(
                new Vector3f(-corner, 2*corner, -corner),
                new Vector3f(FastMath.HALF_PI, 0, 0),
                "carpet_1.jpg"
        );

        String wallTextureName = "carpet_0.jpg";
        builder.createTexturedFlatSurface(
                new Vector3f(-corner, 0, -corner),
                new Vector3f(),
                wallTextureName);

        builder.createTexturedFlatSurface(
                new Vector3f(corner, 0, -corner),
                new Vector3f(0, -FastMath.HALF_PI, 0),
                wallTextureName);

        builder.createTexturedFlatSurface(
                new Vector3f(-corner,0,corner),
                new Vector3f(0, FastMath.HALF_PI, 0),
                wallTextureName);

        builder.createTexturedFlatSurface(
                new Vector3f(corner, 0, corner),
                new Vector3f(0, FastMath.PI, 0),
                wallTextureName);

        String base = "carpet_2.jpg";
        String top = "carpet_3.jpg";
        createRandomPillars(base, top, builder.floorLength(), builder);
        /*builder.createPillar(new Vector2f(2,2), 0.7f, 2, base, top);
        builder.createPillar(new Vector2f(-5, -3), 1, 1.5f, base, top);
        builder.createPillar(new Vector2f(7, 0), 0.4f, 3, base, top);
        builder.createPillar(new Vector2f(5, -2), 0.8f, 1.6f, base, top);
        builder.createPillar(new Vector2f(1, -3), 0.6f, 0.6f, base, top);
        builder.createPillar(new Vector2f(-2, 3), 0.9f, 1f, base, top);
        builder.createPillar(new Vector2f(0, 0), 0.7f, 2.2f, base, top);*/
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
        float width = 0.7f * random.nextFloat() + MINIMAL_PILLAR_WIDTH;
        float height = (float) Math.pow(Math.E, 2 * random.nextFloat()) - 1 + MINIMAL_PILLAR_HEIGHT;
        builder.createPillar(center, width, height, base, top);
    }

    private static Vector2f toVector(List<Float> values) {
        return new Vector2f(
                values.get(0),
                values.get(1));
    }
}
