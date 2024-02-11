package ch.hearc.votingservice.api.web.models.request;

import ch.hearc.votingservice.service.models.Vote;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateVoteRequestBody {

    @NotBlank(message = "L'identifiant de campagne est obligatoire")
    @NotEmpty(message = "L'identifiant de campagne ne doit pas êre vide")
    @Size(min = 5, max = 50, message = "La taille de l'identifiant de campagne doit être comprise entre 5 et 50 caractères")
    private String campagneIdentifiant;

    @NotBlank(message = "Le code d'autorisation est obligatoire")
    @NotEmpty(message = "Le code d'autorisation ne doit pas êre vide")
    @Size(min = 5, max = 50, message = "La taille du code d'autorisation doit être comprise entre 5 et 50 caractères")
    private String autorisationCode;

    @NotBlank(message = "L'identifiant d'objet est obligatoire")
    @NotEmpty(message = "L'identifiant d'objet ne doit pas êre vide")
    @Size(min = 5, max = 50, message = "La taille de l'identifiant d'objet doit être comprise entre 5 et 50 caractères")
    private String objetIdentifiant;


    public static Vote toVote(CreateVoteRequestBody createVoteRequestBody) {

        return Vote.newInstance(createVoteRequestBody.getCampagneIdentifiant(),
                createVoteRequestBody.getAutorisationCode(),
                createVoteRequestBody.getObjetIdentifiant());
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }


    public CreateVoteRequestBody(){}
    public CreateVoteRequestBody(String campagneIdentifiant, String autorisationCode, String objetIdentifiant) {
        this.campagneIdentifiant = campagneIdentifiant;
        this.autorisationCode = autorisationCode;
        this.objetIdentifiant = objetIdentifiant;
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }

    public void setAutorisationCode(String autorisationCode) {
        this.autorisationCode = autorisationCode;
    }
}
