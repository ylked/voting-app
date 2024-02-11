package ch.hearc.adminservice.api.web.models.response;

public class NoEntityFoundResponseBody {


    private String id;
    private String message = "Entity with id %s not found";

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public NoEntityFoundResponseBody(String id){
        this.id = id;
        this.message = String.format(message, id);

    }
}
