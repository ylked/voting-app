package ch.hearc.votingservice.repository.models;

import ch.hearc.votingservice.shared.DemandeStatus;
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

    @Enumerated(EnumType.STRING)
    private DemandeStatus status;

    private String rejectedReason;

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReaason) {
        this.rejectedReason = rejectedReaason;
    }

    public DemandeEntity() {
    }

    public DemandeStatus getStatus() {
        return status;
    }

    public void setStatus(DemandeStatus status) {
        this.status = status;
    }

    public DemandeEntity(String nom, String identifiant, String prenom, String campagneId, DemandeStatus status) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.campagneId = campagneId;
        this.status = status;
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
