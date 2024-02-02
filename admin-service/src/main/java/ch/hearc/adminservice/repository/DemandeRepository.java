package ch.hearc.adminservice.repository;

import ch.hearc.adminservice.repository.entity.DemandeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemandeRepository extends CrudRepository<DemandeEntity,Long> {
    Optional<DemandeEntity> findByIdentifiant(String identifiant);
}
