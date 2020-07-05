package net.starype.gd.physics;

import com.jme3.bullet.control.BetterCharacterControl;
import com.simsilica.es.EntityComponent;

public class PlayerPhysicsComponent implements EntityComponent {

    private BetterCharacterControl control;

    public PlayerPhysicsComponent(BetterCharacterControl control) {
        this.control = control;
    }

    public BetterCharacterControl getControl() {
        return control;
    }
}
