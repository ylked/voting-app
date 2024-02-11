package ch.hearc.adminservice.service.models;

import java.util.Objects;

public class Vote {

    private String campagneIdentifiant;
    private String objetIdentifiant;
    private String autorisationCode;

    private Vote(String autorisationCode, String campagneIdentifiant, String objetIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
        this.autorisationCode = autorisationCode;
        this.objetIdentifiant = objetIdentifiant;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public static Vote nouveauVote(String autorisationCode, String campagneIdentifiant, String objetIdentifiant) {
        Objects.requireNonNull(autorisationCode);
        Objects.requireNonNull(campagneIdentifiant);
        Objects.requireNonNull(objetIdentifiant);
        return new Vote(autorisationCode,campagneIdentifiant,objetIdentifiant);

    }
}
