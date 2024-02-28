package ch.hearc.votingservice.remote.models;


import ch.hearc.votingservice.shared.CampagneStatus;

import java.util.List;

public class CampagneBody {

    private String nom;
    private CampagneStatus status;
    private String identifiant;
    private List<ObjetBody> objets;

    public CampagneBody(String nom, CampagneStatus status, String identifiant, List<ObjetBody> objets) {
        this.nom = nom;
        this.status = status;
        this.identifiant = identifiant;
        this.objets = objets;
    }

    public String getNom() {
        return nom;
    }

    public CampagneStatus getStatus() {
        return status;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public List<ObjetBody> getObjets() {
        return objets;
    }
}