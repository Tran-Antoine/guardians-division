 package net.starype.guardians_division.root_server.networking.database;

public class Credentials {

    private String uuid;
    private String password;
    private String pseudonym;

    public Credentials(String uuid, String password, String pseudonym) {
        this.uuid = uuid;
        this.password = password;
        this.pseudonym = pseudonym;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }
}
