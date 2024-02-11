package ch.hearc.votingservice.jms.models;

public class VoteMessage {

    private String campagneIdentifiant;

    private String objetIdentifiant;

    private String autorisationCode;

    public VoteMessage(String campagneIdentifiant, String objetIdentifiant, String autorisationCode) {
        this.campagneIdentifiant = campagneIdentifiant;
        this.objetIdentifiant = objetIdentifiant;
        this.autorisationCode = autorisationCode;
    }

    public VoteMessage() {
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
}
