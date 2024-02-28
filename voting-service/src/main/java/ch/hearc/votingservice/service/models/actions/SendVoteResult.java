package ch.hearc.votingservice.service.models.actions;

public class SendVoteResult {

    private String campagneIdentifiant;


    private String autorisatonCode;

    private String message;

    private Boolean isSuccess;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Boolean isSuccess(){
        return isSuccess;
    }

    public static SendVoteResult ok(String campagneIdentifiant, String autorisationCode, String message) {
        SendVoteResult result = new SendVoteResult();
        result.campagneIdentifiant = campagneIdentifiant;
        result.message = message;
        result.autorisatonCode = autorisationCode;
        result.isSuccess = Boolean.TRUE;
        return result;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public void setCampagneIdentifiant(String campagneIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
    }

    public String getAutorisatonCode() {
        return autorisatonCode;
    }

    public void setAutorisatonCode(String autorisatonCode) {
        this.autorisatonCode = autorisatonCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static SendVoteResult ko(String campagneIdentifiant, String autorisationCode, String message) {
        SendVoteResult result = new SendVoteResult();
        result.campagneIdentifiant = campagneIdentifiant;
        result.message = message;
        result.autorisatonCode = autorisationCode;
        result.isSuccess = Boolean.FALSE;
        return result;
    }
}
