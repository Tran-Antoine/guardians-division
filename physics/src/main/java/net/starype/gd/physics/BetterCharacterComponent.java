package net.starype.gd.physics;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class BetterCharacterComponent implements EntityComponent {

    private BetterCharacterControl control;
    private Vector3f initialPosition;

    public BetterCharacterComponent(BetterCharacterControl control, Vector3f initialPosition) {
        this.control = control;
        this.initialPosition = initialPosition;
    }

    public BetterCharacterControl getControl() {
        return control;
    }

    public Vector3f getInitialPosition() {
        return initialPosition;
    }
}
