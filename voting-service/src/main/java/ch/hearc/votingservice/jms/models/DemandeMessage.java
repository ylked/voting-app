package ch.hearc.votingservice.jms.models;

import ch.hearc.votingservice.service.models.Demande;

public class DemandeMessage {

    public DemandeMessage(String nom, String identifiant, String prenom, String campagneId) {
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

    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;

}
