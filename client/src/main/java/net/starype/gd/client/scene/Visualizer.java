package net.starype.gd.client.scene;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import net.starype.gd.physics.component.InitialPositionComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Visualizer extends AbstractAppState {

    private Node rootNode;
    private EntitySet entities;
    private Map<EntityId, Spatial> idMap;

    public Visualizer(Node rootNode, EntityData entityData) {
        this.rootNode = rootNode;
        this.entities = entityData.getEntities(ShapeComponent.class, InitialPositionComponent.class);
        this.idMap = new HashMap<>();
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
            Spatial spatial = idMap.get(entity.getId());
            placeEntity(entity, spatial);
        }
    }

    private void hideEntities(Set<Entity> removedEntities) {
        for(Entity entity : removedEntities) {
           idMap.remove(entity.getId()).removeFromParent();
        }
    }

    private void showEntities(Set<Entity> entities) {
        for(Entity entity : entities) {
            renderThenPlace(entity);
        }
    }

    private void renderThenPlace(Entity entity) {
        Spatial geometry = createAndRenderEntity(entity);
        placeEntity(entity, geometry);
    }

    private Spatial createAndRenderEntity(Entity entity) {
        Spatial geometry = entity.get(ShapeComponent.class).getShape();
        rootNode.attachChild(geometry);
        idMap.put(entity.getId(), geometry);
        return geometry;
    }

    private void placeEntity(Entity entity, Spatial geometry) {
        InitialPositionComponent position = entity.get(InitialPositionComponent.class);
        geometry.setLocalTranslation(position.getLocation());
        Vector3f rot = position.getRotation();
        geometry.setLocalRotation(new Quaternion().fromAngles(rot.x, rot.y, rot.z));
    }
}
