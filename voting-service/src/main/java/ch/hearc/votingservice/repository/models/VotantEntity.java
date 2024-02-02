package ch.hearc.votingservice.repository.models;

import jakarta.persistence.*;

@Entity
@Table(name = "votant")
public class VotantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifiant;
    private String nom;
    private String prenom;
    private String autorisationCode;
    private Boolean isVoteDone = Boolean.FALSE;
    private String campagneIdentifiant;

    public VotantEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }

    public Boolean getVoteDone() {
        return isVoteDone;
    }

    public void setVoteDone(Boolean voteDone) {
        isVoteDone = voteDone;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public void setCampagneIdentifiant(String campagneId) {
        this.campagneIdentifiant = campagneId;
    }
}
