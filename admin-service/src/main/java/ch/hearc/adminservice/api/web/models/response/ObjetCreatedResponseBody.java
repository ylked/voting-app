package ch.hearc.adminservice.api.web.models.response;

public class ObjetCreatedResponseBody {

    private String message = "Objet successfully added to campagne";

    private String campagneIdentifiant;

    private String objetIdentifiant;

    public ObjetCreatedResponseBody(String campagneIdentifiant, String objetIdentifiant){
        this.campagneIdentifiant = campagneIdentifiant;
        this.objetIdentifiant = objetIdentifiant;
    }

    public String getMessage() {
        return message;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }
}
