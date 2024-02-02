package ch.hearc.adminservice.service.models.actions;

import ch.hearc.adminservice.service.models.Demande;

public class ReceptionnerDemandeResult {

    private Demande demande;

    private Boolean isSuccess;

    private String message;

    public static ReceptionnerDemandeResult ok(Demande demande, String message) {
        ReceptionnerDemandeResult result = new ReceptionnerDemandeResult();
        result.setDemande(demande);
        result.setMessage(message);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static ReceptionnerDemandeResult ko(Demande demande, String message) {
        ReceptionnerDemandeResult result = new ReceptionnerDemandeResult();
        result.setDemande(demande);
        result.setMessage(message);
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
