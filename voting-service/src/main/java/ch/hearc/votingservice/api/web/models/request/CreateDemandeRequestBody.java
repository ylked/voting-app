package ch.hearc.votingservice.api.web.models.request;

import ch.hearc.votingservice.service.models.Demande;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateDemandeRequestBody {

    @NotBlank(message = "Le nom est obligatoire")
    @NotEmpty(message = "Le nom ne doit pas êre vide")
    @Size(min = 2, max = 50, message = "La taille du nom doit être comprise entre 2 et 50 caractères")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @NotEmpty(message = "Le prenom ne doit pas êre vide")
    @Size(min = 2, max = 50, message = "La taille du prenom doit être comprise entre 2 et 50 caractères")
    private String prenom;

    @NotBlank(message = "L'identifiant de campagne est obligatoire")
    @NotEmpty(message = "L'identifiant de campagne ne doit pas êre vide")
    private String campagneId;

    public CreateDemandeRequestBody(String nom, String prenom, String campagneId) {
        this.nom = nom;
        this.prenom = prenom;
        this.campagneId = campagneId;
    }

    public CreateDemandeRequestBody() {
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCampagneId(String campagneId) {
        this.campagneId = campagneId;
    }

    public static Demande toDemande(CreateDemandeRequestBody demandeBody) {

        return  Demande.nouvelleDemande(demandeBody.getNom(), demandeBody.getPrenom(), demandeBody.getCampagneId());
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
}
