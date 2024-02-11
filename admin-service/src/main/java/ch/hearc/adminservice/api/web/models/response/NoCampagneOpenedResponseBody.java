package ch.hearc.adminservice.api.web.models.response;

public class NoCampagneOpenedResponseBody {


    private String id;
    private String message = "Campagne with id %s not open";

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public NoCampagneOpenedResponseBody(String id){
        this.id = id;
        this.message = String.format(message, id);

    }
}
