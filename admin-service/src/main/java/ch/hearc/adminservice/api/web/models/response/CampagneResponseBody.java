package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.api.web.models.ObjetBody;
import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.Objet;
import ch.hearc.adminservice.shared.CampagneStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * DTO pour le body servant à la liste des capagnes. Sans les liens avec les objets
 * Attention: les getters sont indispensable pour la sérialisation
 */
public class CampagneResponseBody {


    private String nom;
    private CampagneStatus status;
    private String identifiant;
    private List<ObjetBody> objets;
    private LocalDateTime dateCreation;

    private static ObjetBody apply(Objet objet) {
        return new ObjetBody(objet.getNom(), objet.getIdentifiant());
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

    public String getDateCreation() {
        return dateCreation.toString();
    }

    public long getDaysFromCreation() {
        return ChronoUnit.DAYS.between(dateCreation, LocalDateTime.now());
    }

    public List<ObjetBody> getObjets() {
        return objets;
    }

    private CampagneResponseBody(String nom, String identifiant, CampagneStatus status, List<ObjetBody> objets, LocalDateTime dateCreation) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.status = status;
        this.objets = objets;
        this.dateCreation = dateCreation;
    }


    public static CampagneResponseBody mapFromCampagne(Campagne campagne) {

        return new CampagneResponseBody(
                campagne.getNom(),
                campagne.getIdentifiant(),
                campagne.getStatus(),
                    campagne.getObjets().stream().map(CampagneResponseBody::apply).toList(),
                campagne.getDateCreation()
                );
    }


}


