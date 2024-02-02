package ch.hearc.adminservice.repository;

import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.shared.CampagneStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampagneRespository extends CrudRepository<CampagneEntity,Long> {

    Optional<CampagneEntity> findByIdentifiant(String identifiant);

    List<CampagneEntity> findByStatus(CampagneStatus status);
}
