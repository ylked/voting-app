package ch.hearc.votingservice.jms;

import ch.hearc.votingservice.jms.models.DemandeMessage;
import ch.hearc.votingservice.jms.models.TestMessage;
import ch.hearc.votingservice.jms.models.VoteMessage;
import ch.hearc.votingservice.service.models.Demande;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JmsProducteur {

    void sendDemande(DemandeMessage demande);

    void sendVote(VoteMessage vote);

    void sendTestMessage(TestMessage message);
}
