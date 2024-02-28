package ch.hearc.votingservice.remote.models;

public class ObjetBody {
    private String nom;
    //Identifiant métier de l'objet
    private String identifiant;

    public ObjetBody(String nom, String identifiant) {
        this.nom = nom;
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }
}