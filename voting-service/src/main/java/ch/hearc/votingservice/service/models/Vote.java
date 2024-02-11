package ch.hearc.votingservice.service.models;

import java.util.Objects;

public class Vote {

    private String campagneIdentifiant;
    private String objetIdentifiant;
    private String autorisationCode;

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    private Vote(String campagneIdentifiant, String autorisationCode, String objetIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
        this.autorisationCode = autorisationCode;
        this.objetIdentifiant  = objetIdentifiant;
    }

    public static Vote newInstance(String campagneIdentifiant, String autorisationCode, String objetIdentifiant) {
        Objects.requireNonNull(campagneIdentifiant);
        Objects.requireNonNull(autorisationCode);
        Objects.requireNonNull(objetIdentifiant);
        return new Vote(campagneIdentifiant,autorisationCode,objetIdentifiant);
    }
}
