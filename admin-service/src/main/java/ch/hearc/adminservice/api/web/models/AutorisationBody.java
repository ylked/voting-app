package ch.hearc.adminservice.api.web.models;

public class AutorisationBody {

    private String autorisationCode;

    private String campagneIdentifiant;

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public AutorisationBody(String autorisationCode, String campagneIdentifiant){
        this.autorisationCode = autorisationCode;
        this.campagneIdentifiant = campagneIdentifiant;
    }
}
