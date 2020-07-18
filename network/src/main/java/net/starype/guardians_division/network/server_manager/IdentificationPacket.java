package net.starype.guardians_division.network.server_manager;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

import java.util.UUID;

@Serializable
public class IdentificationPacket extends AbstractMessage {

    private String uid;
    private String password;

    public IdentificationPacket(){}

    public IdentificationPacket(String uid, String pass){
        this.uid = uid;
        this.password = pass;
    }

    public UUID getUniqueId() {
        return UUID.fromString(uid);
    }

    public String getPassword() {
        return password;
    }
}
