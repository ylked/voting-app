package ch.hearc.votingservice.api.web.models.response;

public class CreateDemandeResponseBody {

    private String message;

    private String campagneIdentifiant;

    private String demandeIdentifiant;

    public CreateDemandeResponseBody(String message, String campagneIdentifiant, String demandeIdentifiant) {
        this.message = message;
        this.demandeIdentifiant = demandeIdentifiant;
        this.campagneIdentifiant = campagneIdentifiant;
    }


    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getDemandeIdentifiant() {
        return demandeIdentifiant;
    }

    public String getMessage() {
        return message;
    }


}
