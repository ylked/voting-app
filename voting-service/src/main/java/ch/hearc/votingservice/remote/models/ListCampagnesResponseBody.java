package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.api.web.models.CampagneBody;
import ch.hearc.adminservice.api.web.models.ObjetBody;
import ch.hearc.adminservice.service.models.Campagne;

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

    public static ListCampagnesResponseBody mapFromListCampagne(List<Campagne> campagnes) {

        List<CampagneBody> campagnesBody = campagnes.stream().map(campagne -> new CampagneBody(
                campagne.getNom(),
                campagne.getStatus(),
                campagne.getIdentifiant(),
                campagne.getObjets().stream().map(objet ->
                        new ObjetBody(objet.getNom(), objet.getIdentifiant())).toList())).toList();

        return new ListCampagnesResponseBody(campagnesBody.size(),campagnesBody);

    }

    public Integer getNbCampagnes() {
        return total;
    }

    public List<CampagneBody> getCampagnes() {
        return campagnes;
    }
}
