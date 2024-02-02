package ch.hearc.votingservice.service.models;

import ch.hearc.votingservice.remote.models.CampagneResponseBody;
import ch.hearc.votingservice.shared.CampagneStatus;

import java.util.List;

public class Campagne {

    private String nom;
    private String identifiant;

    private CampagneStatus status;

    List<Objet> objets;

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

    public List<Objet> getObjets() {
        return objets;
    }

    public void setObjets(List<Objet> objets) {
        this.objets = objets;
    }

    public Campagne(String nom, String identifiant, CampagneStatus status, List<Objet> objets) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.status = status;
        this.objets = objets;
    }



    public String getNom() {
        return nom;
    }


    public String getIdentifiant() {
        return identifiant;
    }

    public static Campagne fromCampagneResponsBody(CampagneResponseBody campagneResponseBody){

        return new Campagne(
                campagneResponseBody.getNom(),
                campagneResponseBody.getIdentifiant(),
                campagneResponseBody.getStatus(),
                campagneResponseBody.getObjets().stream().map(objetResponseBody -> {
                    return new Objet(campagneResponseBody.getNom(), campagneResponseBody.getIdentifiant());
                }).toList()
        );
    }
}
