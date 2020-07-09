package net.starype.gd.client.scene;

import com.jme3.scene.Spatial;
import com.simsilica.es.EntityComponent;

public class ShapeComponent implements EntityComponent {

    private Spatial shape;

    public ShapeComponent(Spatial shape) {
        this.shape = shape;
    }

    public Spatial getShape() {
        return shape;
    }
}
