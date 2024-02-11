package ch.hearc.adminservice.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "invalid_vote")
public class InvalidVotingAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invalidReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getCamapgneIdentifiant() {
        return camapgneIdentifiant;
    }

    public void setCamapgneIdentifiant(String camapgneIdentifiant) {
        this.camapgneIdentifiant = camapgneIdentifiant;
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

    private String camapgneIdentifiant;

    private String objetIdentifiant;

    private String autorisationCode;

    public static InvalidVotingAttemptEntity newInstance(String campagneIdentifiant, String objetIdentifiant, String autorisationCode, String reasonMessage) {
        InvalidVotingAttemptEntity entity = new InvalidVotingAttemptEntity();
        entity.invalidReason = reasonMessage;
        entity.camapgneIdentifiant = campagneIdentifiant;
        entity.objetIdentifiant = objetIdentifiant;
        entity.autorisationCode = autorisationCode;
        return entity;
    }


}
