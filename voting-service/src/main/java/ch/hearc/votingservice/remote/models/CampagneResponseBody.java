package ch.hearc.votingservice.remote.models;

import ch.hearc.votingservice.shared.CampagneStatus;


import java.util.List;

public class CampagneResponseBody {
    private String nom;
    private String identifiant;

    private CampagneStatus status;

    private List<ObjetResponseBody> objets;

    public List<ObjetResponseBody> getObjets() {
        return objets;
    }

    public void setObjets(List<ObjetResponseBody> objets) {
        this.objets = objets;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public CampagneStatus getStatus() {
        return status;
    }

    public void setStatus(CampagneStatus status) {
        this.status = status;
    }

    public CampagneResponseBody(String nom, String identifiant) {
        this.nom = nom;
        this.identifiant = identifiant;
    }

    public CampagneResponseBody(String nom, String identifiant, CampagneStatus status) {
        this(nom,identifiant);
        this.status = status;
    }

    public CampagneResponseBody() {
    }


    public String getNom() {
        return nom;
    }


    public String getIdentifiant() {
        return identifiant;
    }
}
