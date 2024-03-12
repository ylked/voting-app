package ch.hearc.adminservice.jms.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VoteBroadCastMessage {

    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String campagneIdentifiant;
    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String objetIdentifiant;

    public VoteBroadCastMessage(String campagneIdentifiant, String objetIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
        this.objetIdentifiant = objetIdentifiant;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }
}
