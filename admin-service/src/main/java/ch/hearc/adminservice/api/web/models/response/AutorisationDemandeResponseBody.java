package ch.hearc.adminservice.api.web.models.response;

public class AutorisationDemandeResponseBody {

    private String autorisationCode;

    private String campagneId;

    public AutorisationDemandeResponseBody() {
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }

    public String getCampagneId() {
        return campagneId;
    }

    public void setCampagneId(String campagneId) {
        this.campagneId = campagneId;
    }

    public AutorisationDemandeResponseBody(String autorisationCode, String campagneId) {
        this.autorisationCode =autorisationCode;
        this.campagneId = campagneId;
    }
}
