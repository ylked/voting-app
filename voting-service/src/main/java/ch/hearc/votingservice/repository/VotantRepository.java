package ch.hearc.votingservice.repository;

import ch.hearc.votingservice.repository.models.VotantEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VotantRepository extends CrudRepository<VotantEntity,Long> {

    Optional<VotantEntity> findByAutorisationCodeAndCampagneIdentifiant(String AutorisationCode, String CampagneIdentifiant);
}
