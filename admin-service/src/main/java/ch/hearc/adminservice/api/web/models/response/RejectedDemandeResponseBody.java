package ch.hearc.adminservice.api.web.models.response;

public class RejectedDemandeResponseBody {


    private String demandeId;


    private String message = "Autorisation successfully rejected";


    public String getDemandeId() {
        return demandeId;
    }


    public String getMessage() {
        return message;
    }

    public RejectedDemandeResponseBody(String demandeId) {
        this.demandeId = demandeId;
    }
}
