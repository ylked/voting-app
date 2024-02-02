package ch.hearc.votingservice.service.models;

import ch.hearc.votingservice.jms.models.DemandeMessage;
import ch.hearc.votingservice.repository.models.DemandeEntity;

import java.util.Objects;
import java.util.UUID;

public class Demande {

    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;

    public static DemandeEntity toEntity(Demande demande) {
        return new DemandeEntity(demande.nom, demande.identifiant, demande.prenom, demande.campagneId);
    }

    public static Demande fromEntity(DemandeEntity demandeEntity){
        return new Demande(demandeEntity.getNom(),demandeEntity.getPrenom(),demandeEntity.getCampagneId());
    }

    public static DemandeMessage toDemandeMessage(Demande demande){
        return new DemandeMessage(demande.nom, demande.identifiant, demande.prenom, demande.campagneId);
    }

    public static Demande nouvelleDemande(String nom, String prenom, String campagneId){
        Objects.requireNonNull(nom);
        Objects.requireNonNull(prenom);
        Objects.requireNonNull(campagneId);
        return new Demande(nom, prenom,campagneId);
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

    private Demande(String nom, String prenom, String campagneId) {
        this.nom = nom;
        this.prenom = prenom;
        this.campagneId = campagneId;
        this.identifiant = UUID.randomUUID().toString();
    }

}
