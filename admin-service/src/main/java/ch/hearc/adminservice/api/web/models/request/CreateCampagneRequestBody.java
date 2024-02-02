package ch.hearc.adminservice.api.web.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * DTO sérialisant l'objet json représentant une capagne de vote envoyé à l'API
 */
@Schema(description = "DTO pour création de campagne")
public class CreateCampagneRequestBody {

    @NotBlank(message = "Le nom est obligatoire")
    @NotEmpty(message = "Le nom ne doit pas êre vide")
    @Size(min = 5, max = 50, message = "La taille du nom doit être comprise entre 5 et 50 caractères")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Nom de la campagne", example = "Votation populaire 2022")
    private String nom;

    public String getNom() {
        return nom;
    }

}
