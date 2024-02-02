package ch.hearc.adminservice.service.models;

import ch.hearc.adminservice.jms.models.RefusAutorisationMessaage;

public class RefusAutorisation {

    private String demandeIdentifiant;

    private String message;

    public RefusAutorisation(String demandeIdentifiant, String message) {
        this.demandeIdentifiant = demandeIdentifiant;
        this.message = message;
    }

    public static RefusAutorisationMessaage toJmsMessage(RefusAutorisation refusAutorisation){

        RefusAutorisationMessaage refusAutorisationMessaage = new RefusAutorisationMessaage();
        refusAutorisationMessaage.setMessage(refusAutorisation.getMessage());
        refusAutorisation.setDemandeIdentifiant(refusAutorisation.getDemandeIdentifiant());
        return refusAutorisationMessaage;
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
