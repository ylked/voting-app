package ch.hearc.adminservice.jms.models;

public class RefusAutorisationMessage {

    private String demandeIdentifiant;

    private String message;

    private String campagneIdentifiant;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDemandeIdentifiant() {
        return demandeIdentifiant;
    }

    public String getMessage() {
        return message;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public void setDemandeIdentifiant(String demandeIdentifiant) {
        this.demandeIdentifiant = demandeIdentifiant;
    }

    public void setCampagneIdentifiant(String campagneIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
    }
}
