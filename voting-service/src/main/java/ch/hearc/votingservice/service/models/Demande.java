package ch.hearc.votingservice.service.models;

import ch.hearc.votingservice.jms.models.DemandeMessage;
import ch.hearc.votingservice.repository.models.DemandeEntity;
import ch.hearc.votingservice.shared.DemandeStatus;

import java.util.Objects;
import java.util.UUID;

public class Demande {

    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;

    private DemandeStatus status;

    private Demande(String identifiant, String nom, String prenom, DemandeStatus status, String campagneId) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.status = status;
        this.campagneId = campagneId;
    }

    private Demande(String nom, String prenom, String campagneId, DemandeStatus status) {
        this.nom = nom;
        this.prenom = prenom;
        this.campagneId = campagneId;
        this.identifiant = UUID.randomUUID().toString();
        this.status = status;
    }
    public static DemandeEntity toEntity(Demande demande) {
        return new DemandeEntity(demande.nom, demande.identifiant, demande.prenom, demande.campagneId, demande.status);
    }

    public static Demande fromEntity(DemandeEntity demandeEntity){
        return new Demande(demandeEntity.getIdentifiant(),demandeEntity.getNom(),demandeEntity.getPrenom(),demandeEntity.getStatus(),demandeEntity.getCampagneId());
    }

    public static DemandeMessage toDemandeMessage(Demande demande){
        return new DemandeMessage(demande.nom, demande.identifiant, demande.prenom, demande.campagneId);
    }

    public static Demande nouvelleDemande(String nom, String prenom, String campagneId){
        Objects.requireNonNull(nom);
        Objects.requireNonNull(prenom);
        Objects.requireNonNull(campagneId);
        return new Demande(nom, prenom,campagneId, DemandeStatus.PENDING);
    }

    public static Demande mapFromEntity(DemandeEntity demandeEntity) {

        return new Demande(
                demandeEntity.getIdentifiant(),
                demandeEntity.getNom(),
                demandeEntity.getPrenom(),
                demandeEntity.getStatus(),
                demandeEntity.getCampagneId());
    }

    public String getNom() {
        return nom;
    }



    public String getPrenom() {
        return prenom;
    }



    public String getCampagneId() {
        return campagneId;
    }



    public String getIdentifiant() {
        return identifiant;
    }



    public DemandeStatus getStatus() {
        return status;
    }
}
