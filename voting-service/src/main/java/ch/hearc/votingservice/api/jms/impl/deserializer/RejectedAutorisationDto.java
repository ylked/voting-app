package ch.hearc.votingservice.api.jms.impl.deserializer;

public class RejectedAutorisationDto {

    private String demandeIdentifiant;

    private String message;

    private String campagneIdentifiant;

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
