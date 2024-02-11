package ch.hearc.adminservice.repository;

import ch.hearc.adminservice.repository.entity.ObjetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjetRepository extends CrudRepository<ObjetEntity,Long> {

    Optional<ObjetEntity> findByIdentifiant(String identifiant);
}
