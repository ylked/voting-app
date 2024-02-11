package ch.hearc.adminservice.service;

import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.Objet;
import ch.hearc.adminservice.service.models.UpdateCampagneStatusAction;
import ch.hearc.adminservice.service.models.actions.CreateObjetForCampagneResult;
import ch.hearc.adminservice.service.models.actions.GetCampagneResultsAction;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusResult;
import ch.hearc.adminservice.shared.CampagneStatus;

import java.util.List;
import java.util.Optional;

public interface CampagneService {
    /**
     * Création d'une campagne de vote
     * @param nom le nom de la campagne
     * @return une instance de campagne
     */
    Campagne createCampagne(String nom);

    List<Campagne> getCampagnes(Optional<CampagneStatus> status);

    Optional<Campagne> getCampagneByIdentifiant(String identifiant);

    CreateObjetForCampagneResult createObjetForCampagne(Campagne campagne, String nom);

    Optional<Objet> getObjetByIdentifiant(String idObjet);

    UpdateCampagneStatusResult updateCampganeStatus(Campagne campagne, UpdateCampagneStatusAction action);

    GetCampagneResultsAction getResultForCampagne(String campagneIdentifiant);
}
