package ch.hearc.adminservice.jms.impl;


import ch.hearc.adminservice.jms.JmsMessageProducteur;
import ch.hearc.adminservice.jms.models.AutorisationMessage;
import ch.hearc.adminservice.jms.models.RefusAutorisationMessage;
import ch.hearc.adminservice.jms.models.VoteBroadCastMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsMessageProducteurImpl implements JmsMessageProducteur {

    private final Logger LOGGER = LoggerFactory
            .getLogger(JmsMessageProducteurImpl.class);

    @Value("${spring.activemq.demande.accepted.queue}")
    String demandeAcceptedQueue;

    @Value("${spring.activemq.demande.denied.queue}")
    String demandeDeniedQueue;

    @Value("${spring.activemq.vote.broadcast.topic}")
    String voteBroadcastTopic;

    @Autowired
    JmsTemplate jmsTemplate;


    /**
     * Envoie de l'autorisation de vote
     * @param autorisation l'autorisation de vote
     * @throws JsonProcessingException
     */
    @Override
    public void sendAutorisation(AutorisationMessage autorisation) throws JsonProcessingException {
        try {

            String jsonObj = sendMessage(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(autorisation), demandeAcceptedQueue);

            LOGGER.info("Message send to queue: " + demandeAcceptedQueue + ", message: " + jsonObj);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String sendMessage(String jsonObj, String destination) throws JsonProcessingException {

        jmsTemplate.send(destination, messageCreator -> {
            TextMessage message = messageCreator.createTextMessage();
            message.setText(jsonObj);
            return message;
        });
        return jsonObj;
    }

    @Override
    public void sendVoteBroadcat(VoteBroadCastMessage voteBroadCastMessage) throws JsonProcessingException {

        String jsonObj = sendMessage(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(voteBroadCastMessage), voteBroadcastTopic);

        LOGGER.info("Message send to topic: " + voteBroadcastTopic + ", message: " + jsonObj);
    }
    @Override
    public void sendDeniedAutorisation(RefusAutorisationMessage refusAutorisation) throws JsonProcessingException {
        try {

            String jsonObj = sendMessage(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(refusAutorisation), demandeDeniedQueue);

            LOGGER.info("Message send to queue: " + demandeDeniedQueue + ", message: " + jsonObj);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
