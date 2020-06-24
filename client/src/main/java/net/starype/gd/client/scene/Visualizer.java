package net.starype.gd.client.scene;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import java.util.Set;

public class Visualizer extends AbstractAppState {

    private Node rootNode;
    private EntitySet entities;

    public Visualizer(Node rootNode, EntitySet entities) {
        this.rootNode = rootNode;
        this.entities = entities;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        System.out.println("Visualizer has been initialized");
        super.initialize(stateManager, app);
        showEntities(entities);
    }

    @Override
    public void update(float tpf) {
        if(entities.applyChanges()) {
            showEntities(entities.getAddedEntities());
            updatePosition(entities.getChangedEntities());
            hideEntities(entities.getRemovedEntities());
        }
    }

    private void updatePosition(Set<Entity> changedEntities) {
        for(Entity entity : changedEntities) {
            Spatial spatial = rootNode.getChild(entity.get(ShapeComponent.class).getName());
            placeEntity(entity, spatial);
        }
    }

    private void hideEntities(Set<Entity> removedEntities) {
        for(Entity entity : removedEntities) {
            rootNode.detachChild(rootNode.getChild(entity.get(ShapeComponent.class).getName()));
        }
    }

    private void showEntities(Set<Entity> entities) {
        for(Entity entity : entities) {
            renderThenPlace(entity);
        }
    }

    private void renderThenPlace(Entity entity) {
        Spatial geometry = renderEntity(entity);
        placeEntity(entity, geometry);
    }

    private Spatial renderEntity(Entity entity) {

        ShapeComponent shape = entity.get(ShapeComponent.class);
        Spatial geometry = new Geometry(shape.getName(), shape.getShape());
        geometry.setMaterial(shape.getMaterial());
        rootNode.attachChild(geometry);
        return geometry;
    }

    private void placeEntity(Entity entity, Spatial geometry) {

        PositionComponent position = entity.get(PositionComponent.class);
        geometry.setLocalTranslation(position.getLocation());
        Vector3f rot = position.getRotation();
        geometry.setLocalRotation(new Quaternion().fromAngles(rot.x, rot.y, rot.z));
    }

}
