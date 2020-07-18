package net.starype.guardians_division.network.server_manager;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

import java.util.Optional;
import java.util.stream.Stream;

@Serializable
public class IdentificationAnswerPacket extends AbstractMessage {

    private byte response;

    public IdentificationAnswerPacket(){}

    public IdentificationAnswerPacket(Result result){
        this.response = result.getId();
    }

    public IdentificationAnswerPacket(byte response) {
        this.response = response;
    }

    public byte getResponse() {
        return response;
    }

    public enum Result {
        ACCEPTED(0, null),
        BAD_CREDENTIALS(10, "Wrong credentials"),
        BANNED_PERM(20, "Permanent ban"),
        BANNED_TEMP(21, "Temporary ban");

        byte id;
        String cause;
        Result(int id, String cause){
            this.id = (byte)id;
            this.cause = cause;
        }

        public byte getId() {
            return id;
        }

        public String getCause() {
            return cause;
        }

        public static Optional<Result> getResultById(byte id){
            return Stream.of(values()).filter(result -> result.getId() == id).findFirst();
        }
    }
}
