package ch.hearc.adminservice.api.web.models.response;

public class CampagneCreatedResponseBody {

    private String message = "Campagne successfully created";

    private String campagneIdentifiant;


    public CampagneCreatedResponseBody(String campagneIdentifiant){
        this.campagneIdentifiant = campagneIdentifiant;
    }

    public String getMessage() {
        return message;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }
}
