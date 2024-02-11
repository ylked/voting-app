package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.service.models.Demande;

/**
 * DTO pour la réponse de l'API Rest
 */
public class DemandeResponseBody {

    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;


    private DemandeResponseBody(String nom, String identifiant, String prenom, String campagneId) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.prenom = prenom;
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

    public static DemandeResponseBody mapFromDemande(Demande demande) {
        return new DemandeResponseBody(
                demande.getNom(),  demande.getIdentifiant(), demande.getPrenom(),demande.getCampagneId());
    }
}
