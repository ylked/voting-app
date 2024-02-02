package ch.hearc.votingservice.repository.models;

import jakarta.persistence.*;

@Entity
@Table(name = "demande")
public class DemandeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;

    private Boolean isOnError = Boolean.FALSE;

    public Boolean getOnError() {
        return isOnError;
    }

    public void setOnError(Boolean onError) {
        isOnError = onError;
    }

    public DemandeEntity() {
    }

    public DemandeEntity(String nom, String identifiant, String prenom, String campagneId) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.campagneId = campagneId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCampagneId() {
        return campagneId;
    }

    public void setCampagneId(String campagneId) {
        this.campagneId = campagneId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
