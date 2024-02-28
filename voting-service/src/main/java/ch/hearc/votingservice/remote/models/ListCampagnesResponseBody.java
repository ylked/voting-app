package ch.hearc.votingservice.remote.models;


import ch.hearc.votingservice.service.models.Campagne;

import java.util.List;

/**
 * DTO (Data Tansfert Object) pour le retour de la liste des campagnes
 */
public class ListCampagnesResponseBody {

    private List<CampagneBody> campagnes;
    private Integer total;

    private ListCampagnesResponseBody(Integer nbCampagnes, List<CampagneBody> campagnes) {
        this.total = nbCampagnes;
        this.campagnes = campagnes;
    }



    public Integer getNbCampagnes() {
        return total;
    }

    public List<CampagneBody> getCampagnes() {
        return campagnes;
    }
}
