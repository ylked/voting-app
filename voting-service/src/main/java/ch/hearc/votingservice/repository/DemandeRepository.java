package ch.hearc.votingservice.repository;

import ch.hearc.votingservice.repository.models.DemandeEntity;
import ch.hearc.votingservice.shared.DemandeStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeRepository extends CrudRepository<DemandeEntity,Long> {

    Optional<DemandeEntity> findByIdentifiant(String identifiant);

    List<DemandeEntity> findByStatus(DemandeStatus demandeStatus);
}
