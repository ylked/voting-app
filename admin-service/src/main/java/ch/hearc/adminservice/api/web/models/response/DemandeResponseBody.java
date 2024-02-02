package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.service.models.Demande;

/**
 * DTO pour la réponse de l'API Rest
 */
public class DemandeResponseBody {

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCampagneId(String campagneId) {
        this.campagneId = campagneId;
    }

    public String getNom() {
        return nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCampagneId() {
        return campagneId;
    }

    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;

    public DemandeResponseBody(String nom, String identifiant, String prenom, String campagneId) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.campagneId = campagneId;
    }


    public static DemandeResponseBody fromDemande(Demande demande) {
        return new DemandeResponseBody(
                demande.getNom(),  demande.getIdentifiant(), demande.getPrenom(),demande.getCampagneId());
    }
}
