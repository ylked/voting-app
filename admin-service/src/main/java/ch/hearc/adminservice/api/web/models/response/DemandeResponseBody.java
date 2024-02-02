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

    public DemandeResponseBody(String nom, String identifiant, String prenom, String campagneId) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.campagneId = campagneId;
    }

    public static DemandeResponseBody fromDemande(Demande demande) {
        return new DemandeResponseBody(
                demande.getNom(), demande.getPrenom(), demande.getIdentifiant(), demande.getCampagneId());
    }
}
