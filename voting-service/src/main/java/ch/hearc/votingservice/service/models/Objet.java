package ch.hearc.votingservice.service.models;

public class Objet {

    //nom de l'objet
    private String nom;

    //Identifiant métier de l'objet
    private String identifiant;

    public Objet(String nom, String identifiant) {
        this.nom = nom;
        this.identifiant = identifiant;
    }

    public Objet() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
}
