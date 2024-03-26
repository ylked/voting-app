package ch.hearc.adminservice.api.web.models.response;

public class CampagneCreatedResponseBody {

    private String message = "Campagne successfully created";

    private String campagneIdentifiant;
    private String dateCreation;


    public CampagneCreatedResponseBody(String campagneIdentifiant, String dateCreationStr){
        this.campagneIdentifiant = campagneIdentifiant;
        this.dateCreation = dateCreationStr;
    }

    public String getMessage() {
        return message;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getDateCreation() {
        return dateCreation;
    }
}
