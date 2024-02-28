package ch.hearc.votingservice.remote;

import ch.hearc.votingservice.remote.models.CampagneResponseBody;
import ch.hearc.votingservice.remote.models.ListCampagnesResponseBody;

import java.util.Optional;

/**
 * Service de récupération distant
 * Appel sur les API de admin-service
 */
public interface AdminRemoteServiceClient {
    /**
     * Retourne les campagnes de votes ouvertes
     *
     * @return la liste des campagnes ouvertes
     */
    ListCampagnesResponseBody getCampagnesOuvertes();

    Boolean isCampagneExistAndOuverte(String campagneIdentifiant);

    Optional<CampagneResponseBody> getCampagneByIdentifiant(String identifiant);
}
