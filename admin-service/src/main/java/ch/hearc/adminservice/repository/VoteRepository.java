package ch.hearc.adminservice.repository;

import ch.hearc.adminservice.repository.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity,Long> {

    Optional<VoteEntity> findByIdentifiant(String identifiant);

    List<VoteEntity> findByObjetEntityIdentifiant(String identfiantObjetNon);
}
