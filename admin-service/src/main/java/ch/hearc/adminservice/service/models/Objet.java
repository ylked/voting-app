package ch.hearc.adminservice.service.models;

import ch.hearc.adminservice.repository.entity.ObjetEntity;

import java.util.UUID;

public class Objet {

    //nom de l'objet
    private String nom;

    //Identifiant métier de l'objet
    private String identifiant;


    public static Objet nouvelObjet(String nom){
        return new Objet(nom);
    }

    private Objet(String nom) {
        this.nom = nom;
        this.identifiant = UUID.randomUUID().toString();
    }

    public Objet(String identifiant, String nom) {
        this.identifiant = identifiant;
        this.nom = nom;
    }

    public Objet(String identifiant, String nom, Campagne campagne) {
        this(identifiant,nom);

    }

    public static Objet mapFromEntity(ObjetEntity entity) {
        return new Objet(entity.getIdentifiant(), entity.getNom());
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





    public static ObjetEntity mapToEntity(Objet objet) {
        return new ObjetEntity(objet.getNom(), objet.getIdentifiant());
    }
}
