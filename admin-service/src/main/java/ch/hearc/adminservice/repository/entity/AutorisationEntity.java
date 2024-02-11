package ch.hearc.adminservice.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "autorisation")
public class AutorisationEntity {

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public CampagneEntity getCampagneEntity() {
        return campagneEntity;
    }

    public void setCampagneEntity(CampagneEntity campagneEntity) {
        this.campagneEntity = campagneEntity;
    }

    public AutorisationEntity() {
    }

    private String identifiant;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    private String autorisationCode;
    private Boolean used = Boolean.FALSE;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campagne_id")
    private CampagneEntity campagneEntity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
