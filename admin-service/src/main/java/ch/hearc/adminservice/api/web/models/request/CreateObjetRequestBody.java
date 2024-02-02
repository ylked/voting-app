package ch.hearc.adminservice.api.web.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateObjetRequestBody {

    @NotBlank(message = "Le nom est obligatoire")
    @NotEmpty(message = "Le nom ne doit pas êre vide")
    @Size(min = 5, max = 50, message = "La taille du nom doit être comprise entre 5 et 50 caractères")
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
