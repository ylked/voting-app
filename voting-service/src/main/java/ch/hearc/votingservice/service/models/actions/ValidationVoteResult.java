package ch.hearc.votingservice.service.models.actions;

public class ValidationVoteResult {

    private Boolean validationOk = Boolean.FALSE;
    private String campagneIdentifiant;

    private String objetIdentifiant;

    private String  autorisationCode;

    private String message;

    public Boolean getValidationOk() {
        return validationOk;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public void setCampagneIdentifiant(String campagneIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }

    public void setObjetIdentifiant(String objetIdentifiant) {
        this.objetIdentifiant = objetIdentifiant;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ValidationVoteResult ko(String campagneIdentifiant, String autorisationCode, String objetIdentifiant, String message) {
        ValidationVoteResult validationVoteResult = new ValidationVoteResult();
        validationVoteResult.campagneIdentifiant = campagneIdentifiant;
        validationVoteResult.validationOk = Boolean.FALSE;
        validationVoteResult.autorisationCode = autorisationCode;
        validationVoteResult.objetIdentifiant = objetIdentifiant;
        validationVoteResult.message = message;
        return validationVoteResult;
    }

    public static ValidationVoteResult ok(String campagneIdentifiant, String autorisationCode, String objetIdentifiant, String message) {
        ValidationVoteResult validationVoteResult = new ValidationVoteResult();
        validationVoteResult.campagneIdentifiant = campagneIdentifiant;
        validationVoteResult.validationOk = Boolean.TRUE;
        validationVoteResult.autorisationCode = autorisationCode;
        validationVoteResult.objetIdentifiant = objetIdentifiant;
        validationVoteResult.message = message;
        return validationVoteResult;
    }

    public Boolean isValidationOk() {
        return validationOk;
    }

    public void setValidationOk(Boolean validationOk) {
        this.validationOk = validationOk;
    }
}
