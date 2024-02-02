package ch.hearc.adminservice.service.models.actions;

import ch.hearc.adminservice.service.models.Autorisation;
import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.Objet;

public class ValidateDemandeResult {

    private Demande demande;

    private Boolean isSuccess;

    private String message;

    private Autorisation autorisation;


    public static ValidateDemandeResult ko(String message) {
        ValidateDemandeResult result = new ValidateDemandeResult();
        result.demande = null;
        result.isSuccess = Boolean.FALSE;
        result.message = message;
        return result;
    }

    public static ValidateDemandeResult ok(String message, Demande demande, Autorisation autorisation) {
        ValidateDemandeResult result = new ValidateDemandeResult();
        result.demande = demande;
        result.isSuccess = Boolean.TRUE;
        result.message = message;
        result.autorisation = autorisation;
        return result;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess(){
        return isSuccess;
    }

    public Autorisation getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }
}
