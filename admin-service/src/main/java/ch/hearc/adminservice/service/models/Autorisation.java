package ch.hearc.adminservice.service.models;

import ch.hearc.adminservice.jms.models.AutorisationMessage;
import ch.hearc.adminservice.repository.entity.AutorisationEntity;

import java.util.UUID;

public class Autorisation {

    private String identifiant;

    private String autorisationCode;

    private String demandeId;

    private String campagneId;

    public static Autorisation validate(Demande demande){
        Autorisation autorisation = new Autorisation();
        autorisation.demandeId = demande.getIdentifiant();
        autorisation.campagneId = demande.getCampagneId();
        autorisation.identifiant = UUID.randomUUID().toString();
        autorisation.autorisationCode = UUID.randomUUID().toString();
        return autorisation;

    }

    public static Autorisation refuser(Demande demande){
        Autorisation autorisation = new Autorisation();
        autorisation.demandeId = demande.getIdentifiant();
        autorisation.campagneId = demande.getCampagneId();
        autorisation.identifiant = UUID.randomUUID().toString();
        return autorisation;

    }

    public static AutorisationEntity toEntity(Autorisation autorisation) {
        AutorisationEntity autorisationEntity = new AutorisationEntity();
        autorisationEntity.setAutorisationCode(autorisation.autorisationCode);
        autorisationEntity.setIdentifiant(autorisation.identifiant);
        return  autorisationEntity;
    }

    /**
     * Conversion d'une autorisation métier en un objet message pour envoi jms
     * @param autorisation l'autorisation métier à convertir
     * @return une instance de AutorisationMessage
     */
    public static AutorisationMessage toJmsMessage(Autorisation autorisation) {
        AutorisationMessage autorisationMessage = new AutorisationMessage();
        autorisationMessage.setAutorisationCode(autorisation.autorisationCode);
        autorisationMessage.setCampagneId(autorisation.campagneId);
        autorisationMessage.setDemandeId(autorisation.demandeId);
        return  autorisationMessage;
    }



    public Autorisation() {
    }

    public static Autorisation mapFromEntity(AutorisationEntity autorisationEntity) {
        Autorisation autorisation = new Autorisation();
        autorisation.setIdentifiant(autorisationEntity.getIdentifiant());
        autorisation.setAutorisationCode(autorisationEntity.getAutorisationCode());
        autorisation.setCampagneId(autorisationEntity.getCampagneEntity().getIdentifiant());
        return autorisation;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }

    public String getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(String demandeId) {
        this.demandeId = demandeId;
    }

    public String getCampagneId() {
        return campagneId;
    }

    public void setCampagneId(String campagneId) {
        this.campagneId = campagneId;
    }
}
