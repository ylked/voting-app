package ch.hearc.adminservice.jms.models;

public class AutorisationMessage {


    private String autorisationCode;

    private String demandeId;

    private String campagneId;

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }

    public AutorisationMessage() {
    }


    public String getAutorisationCode() {
        return autorisationCode;
    }

    public String getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(String demandeId) {
        this.demandeId = demandeId;
    }

    public String getCampagneId() {
        return campagneId;
    }

    public void setCampagneId(String campagneId) {
        this.campagneId = campagneId;
    }
}
