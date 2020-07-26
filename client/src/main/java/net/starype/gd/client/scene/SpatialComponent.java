package net.starype.gd.client.scene;

import com.jme3.scene.Spatial;
import com.simsilica.es.EntityComponent;

public class SpatialComponent implements EntityComponent {

    private Spatial shape;

    public SpatialComponent(Spatial shape) {
        this.shape = shape;
    }

    public Spatial getShape() {
        return shape;
    }
}
