package ch.hearc.votingservice.service.models.actions;

public class ValidateAutorisationResult {

    private String idDemande;

    private String message;

    private String idCampagne;

    public static ValidateAutorisationResult ok(String demandeId, String campagneId, String message) {
        ValidateAutorisationResult result = new ValidateAutorisationResult();
        result.idDemande = demandeId;
        result.idCampagne = campagneId;
        result.message = message;
        return result;
    }

    public String getIdCampagne() {
        return idCampagne;
    }

    public void setIdCampagne(String idCampagne) {
        this.idCampagne = idCampagne;
    }

    public static ValidateAutorisationResult ko(String idDemande, String idCampagne,String message) {
        ValidateAutorisationResult result = new ValidateAutorisationResult();
        result.idDemande = idDemande;
        result.idCampagne = idCampagne;
        result.message = message;
        return result;
    }

    public ValidateAutorisationResult() {
    }

    public String getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(String idDemande) {
        this.idDemande = idDemande;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
