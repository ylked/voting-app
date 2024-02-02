package ch.hearc.votingservice.remote;

import ch.hearc.votingservice.remote.models.CampagneResponseBody;

import java.util.List;
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
    List<CampagneResponseBody> getCampagnesOuvertes();

    Boolean isCampagneExistAndOuverte(String campagneIdentifiant);

    Optional<CampagneResponseBody> getCampagneByIdentifiant(String identifiant);
}
