package net.starype.gd.client.scene;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Mesh;
import com.simsilica.es.EntityComponent;

public class ShapeComponent implements EntityComponent {

    private String name;
    private Mesh shape;
    private Material material;

    public ShapeComponent(String name, Mesh shape, Material material) {
        this.name = name;
        this.shape = shape;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public Mesh getShape() {
        return shape;
    }

    public Material getMaterial() {
        return material;
    }
}
