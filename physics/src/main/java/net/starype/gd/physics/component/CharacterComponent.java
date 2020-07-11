package net.starype.gd.physics.component;

import com.jme3.bullet.control.BetterCharacterControl;
import com.simsilica.es.EntityComponent;

public class CharacterComponent implements EntityComponent {

    private BetterCharacterControl body;

    public CharacterComponent(BetterCharacterControl control) {
        this.body = control;
    }

    public BetterCharacterControl getBody() {
        return body;
    }
}
