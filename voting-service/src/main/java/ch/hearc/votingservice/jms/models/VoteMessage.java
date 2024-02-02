package ch.hearc.votingservice.jms.models;

public class VoteMessage {

    private String camoagneIdentifiant;

    private String objetIdentifiant;

    private String autorisationCode;

    public VoteMessage(String camoagneIdentifiant, String objetIdentifiant, String autorisationCode) {
        this.camoagneIdentifiant = camoagneIdentifiant;
        this.objetIdentifiant = objetIdentifiant;
        this.autorisationCode = autorisationCode;
    }

    public VoteMessage() {
    }

    public String getCamoagneIdentifiant() {
        return camoagneIdentifiant;
    }

    public void setCamoagneIdentifiant(String camoagneIdentifiant) {
        this.camoagneIdentifiant = camoagneIdentifiant;
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
