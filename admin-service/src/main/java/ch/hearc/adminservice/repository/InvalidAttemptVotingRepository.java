package ch.hearc.adminservice.repository;

import ch.hearc.adminservice.repository.entity.InvalidVotingAttemptEntity;
import ch.hearc.adminservice.repository.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvalidAttemptVotingRepository extends CrudRepository<InvalidVotingAttemptEntity,Long> {

}
