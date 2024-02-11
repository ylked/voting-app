package ch.hearc.adminservice.api.jms.models;

import ch.hearc.adminservice.service.models.Demande;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DemandeReceivedMessage {

    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String nom;

    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String identifiant;

    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String prenom;

    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String campagneId;

    public DemandeReceivedMessage(String nom, String identifiant, String prenom, String campagneId) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.prenom = prenom;
        this.campagneId = campagneId;
    }

    public DemandeReceivedMessage() {
    }

    public static Demande toDemande(DemandeReceivedMessage demandeReceivedMessage) {

        return Demande.fromDto(
                demandeReceivedMessage.identifiant,
                demandeReceivedMessage.nom,
                demandeReceivedMessage.prenom,
                demandeReceivedMessage.campagneId);
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
}
