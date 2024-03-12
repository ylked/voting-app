package ch.hearc.adminservice.api.jms.impl;


import ch.hearc.adminservice.api.jms.JmsMessageListener;
import ch.hearc.adminservice.api.jms.deserializer.DemandeJmsDeserializerMapper;
import ch.hearc.adminservice.api.jms.deserializer.JsonDeserialisationException;
import ch.hearc.adminservice.api.jms.deserializer.VoteJmsDeserializerMapper;
import ch.hearc.adminservice.api.jms.models.DemandeReceivedMessage;
import ch.hearc.adminservice.api.jms.models.SoumissionVoteMessage;
import ch.hearc.adminservice.jms.models.VoteBroadCastMessage;
import ch.hearc.adminservice.service.AutorisationService;
import ch.hearc.adminservice.service.VoteService;
import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.Vote;
import ch.hearc.adminservice.service.models.actions.ReceptionnerDemandeResult;
import ch.hearc.adminservice.service.models.actions.VoteSubmitedResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class JmsMessageListenerImpl implements JmsMessageListener {

    @Autowired
    AutorisationService autorisationService;
    @Autowired
    VoteService voteService;
    @Autowired
    DemandeJmsDeserializerMapper demandeMapper;
    @Autowired
    VoteJmsDeserializerMapper voteMapper;

    Logger LOGGER = LoggerFactory.getLogger(JmsMessageListenerImpl.class);

    /**
     * Listener à l'écoute des demandes d'autorisations
     * Une demande d'autorisation permet de recevoir une autorisation de vote.
     * Les demande sont stockées en base de donnée
     * @param jsonMessage
     * @throws JMSException
     */
    @Override
    @JmsListener(destination = "${spring.activemq.demande.submited.queue}")
    public void listenDemandes(final TextMessage jsonMessage) throws JMSException, JsonProcessingException {

        String messageData = null;


        if(jsonMessage != null) {
            messageData = jsonMessage.getText();

            LOGGER.info("Listen demandes message received from queue:");
            LOGGER.info(messageData);

            try{
                DemandeReceivedMessage demandeReceivedMessage = demandeMapper.mapFromJson(messageData);
                Demande demande = DemandeReceivedMessage.toDemande(demandeReceivedMessage);
                ReceptionnerDemandeResult result = autorisationService.receptionnerDemande(demande);
                LOGGER.info("Autorisation demande result: " + result.getMessage());
                LOGGER.info("Autorisation demande isSuccess: " + result.getSuccess());

            }catch(JsonDeserialisationException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    @JmsListener(destination = "${spring.activemq.vote.submit.queue}")
    public void listenVotes(final TextMessage jsonMessage) throws JMSException, JsonProcessingException {

        String messageData = null;


        if(jsonMessage != null) {
            messageData = jsonMessage.getText();

            LOGGER.info("Listen votes message received from queue:");
            LOGGER.info(messageData);

            try {

                Vote vote = SoumissionVoteMessage.toVote(voteMapper.mapFromJson(messageData));

                //TODO Check si autorisation ok
                VoteSubmitedResult voteSubmitedResult = voteService.validateVote(vote);
                LOGGER.info("Vote submitted result: " + voteSubmitedResult.getMessage());

                //Si traitement du vote ok
                if(voteSubmitedResult.getSuccess()){
                    voteService.publishVote(new VoteBroadCastMessage(vote.getCampagneIdentifiant(),vote.getObjetIdentifiant()));
                }

            } catch (JsonDeserialisationException e) {
                throw new RuntimeException(e);
            }

        }
    }
}


