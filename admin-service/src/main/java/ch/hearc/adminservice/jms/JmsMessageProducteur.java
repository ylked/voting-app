package ch.hearc.adminservice.jms;

import ch.hearc.adminservice.jms.models.AutorisationMessage;
import ch.hearc.adminservice.jms.models.RefusAutorisationMessage;
import ch.hearc.adminservice.jms.models.VoteBroadCastMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JmsMessageProducteur {


    void sendAutorisation(AutorisationMessage autorisation) throws JsonProcessingException;


    void sendVoteBroadcat(VoteBroadCastMessage voteBroadCastMessage) throws JsonProcessingException;

    void sendDeniedAutorisation(RefusAutorisationMessage refusAutorisation) throws JsonProcessingException;
}
