package ch.hearc.adminservice.service.models;

import ch.hearc.adminservice.jms.models.RefusAutorisationMessage;

public class RefusAutorisation {

    private String campagneIdentifiant;
    private String demandeIdentifiant;

    private String message;

    public RefusAutorisation(String campagneId,String demandeIdentifiant, String message) {
        this.demandeIdentifiant = demandeIdentifiant;
        this.campagneIdentifiant = campagneId;
        this.message = message;
    }

    public static RefusAutorisationMessage toJmsMessage(RefusAutorisation refusAutorisation){

        RefusAutorisationMessage refusAutorisationMessage = new RefusAutorisationMessage();
        refusAutorisationMessage.setMessage(refusAutorisation.getMessage());
        refusAutorisationMessage.setDemandeIdentifiant(refusAutorisation.getDemandeIdentifiant());
        refusAutorisationMessage.setCampagneIdentifiant(refusAutorisation.campagneIdentifiant);
        return refusAutorisationMessage;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    private void setCampagneIdentifiant(String campagneIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
    }

    public String getDemandeIdentifiant() {
        return demandeIdentifiant;
    }

    public void setDemandeIdentifiant(String demandeIdentifiant) {
        this.demandeIdentifiant = demandeIdentifiant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
