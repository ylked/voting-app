package ch.hearc.adminservice.service;

import ch.hearc.adminservice.service.models.Vote;
import ch.hearc.adminservice.service.models.actions.VoteSubmitedResult;

public interface VoteService {
    VoteSubmitedResult validateVote(Vote vote);
}
