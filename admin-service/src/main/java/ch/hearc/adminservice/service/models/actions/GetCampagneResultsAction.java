package ch.hearc.adminservice.service.models.actions;

import ch.hearc.adminservice.service.models.Campagne;

import java.util.Optional;

/**
 * Résultats de l'opération de retour des résultats d'une campagne
 * 2 conditions métiers:
 * - la campagne doit exsiter
 * - la campagne doit être dans un status OPENED
 */
public class GetCampagneResultsAction {

    private String errorMessage;

    private Boolean isCampagneExist = Boolean.FALSE;

    private Boolean isCampagneCreated = Boolean.FALSE;

    Optional<Campagne> campagne = Optional.empty();

    private GetCampagneResultsAction(String errorMessage, Boolean isCampagneExist, Boolean isCampagneCreated) {
        this.isCampagneExist = isCampagneExist;
        this.isCampagneCreated = isCampagneCreated;
        this.errorMessage = errorMessage;
    }

    private GetCampagneResultsAction(String errorMessage, Boolean isCampagneExist, Boolean isCampagneCreated, Campagne campagne) {
        this(errorMessage,isCampagneExist, isCampagneCreated);
        this.campagne = Optional.of(campagne);
    }

    public static GetCampagneResultsAction campagneNotExist(String campagneIdentifiant) {
        return new GetCampagneResultsAction(
                "The campagne with the provided identifiant ["+campagneIdentifiant+"] doesn't exist",
                Boolean.FALSE,Boolean.FALSE);

    }

    public static GetCampagneResultsAction campagneInStatusCreated(String campagneIdentifiant) {
        return new GetCampagneResultsAction(
                "The campagne with the provided identifiant ["+campagneIdentifiant+"] is in status CREATED",
                Boolean.TRUE,Boolean.TRUE);
    }

    public static GetCampagneResultsAction ok(Campagne campagne) {
        return new GetCampagneResultsAction(
                "",
                Boolean.TRUE,Boolean.FALSE, campagne);
    }

    public boolean isCampagneExist() {
        return this.isCampagneExist;
    }

    public boolean InStatusCreated() {
        return this.isCampagneCreated;
    }

    public Campagne getCampagne() {
        return this.campagne.get();
    }
}
