package ch.hearc.votingservice.api.web.models.response;

import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.shared.DemandeStatus;

public class DemandeResponseBody {
    private String nom;

    private String identifiant;

    private String prenom;

    private String campagneId;

    private DemandeStatus status;

    public DemandeResponseBody(String identifiant, String nom, String prenom, String campagneId, DemandeStatus status) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.campagneId = campagneId;
        this.status = status;
    }

    public static DemandeResponseBody mapFromDemande(Demande demande) {

        return new DemandeResponseBody(demande.getIdentifiant(),
                demande.getNom(),
                demande.getPrenom(),
                demande.getCampagneId(),
                demande.getStatus());

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

    public DemandeStatus getStatus() {
        return status;
    }
}
