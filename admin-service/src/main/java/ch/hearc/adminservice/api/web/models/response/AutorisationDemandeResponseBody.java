package ch.hearc.adminservice.api.web.models.response;

public class AutorisationDemandeResponseBody {

    private String autorisationCode;

    private String campagneId;

    private String message = "Autorisation successfully created";

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public String getCampagneId() {
        return campagneId;
    }

    public String getMessage() {
        return message;
    }

    public AutorisationDemandeResponseBody(String autorisationCode, String campagneId) {
        this.autorisationCode =autorisationCode;
        this.campagneId = campagneId;
    }
}
