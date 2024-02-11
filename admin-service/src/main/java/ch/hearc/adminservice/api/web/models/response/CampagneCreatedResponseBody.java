package ch.hearc.adminservice.api.web.models.response;

public class ObjetCreatedResponseBody {

    private String message = "Objet [%s] successfully added to campagne [%s]";

    private String campagneIdentifiant;

    private String objetIdentifiant;

    public ObjetCreatedResponseBody(String campagneIdentifiant, String objetIdentifiant){
        this.message = String.format(this.message, campagneIdentifiant, objetIdentifiant);
    }

    public String getMessage() {
        return message;
    }
}
