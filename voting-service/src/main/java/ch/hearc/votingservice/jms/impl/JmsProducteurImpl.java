package ch.hearc.votingservice.jms.impl;

import ch.hearc.votingservice.jms.JmsProducteur;
import ch.hearc.votingservice.jms.models.DemandeMessage;
import ch.hearc.votingservice.jms.models.TestMessage;
import ch.hearc.votingservice.jms.models.VoteMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Composant permettant la soumission de message sur le bus jms
 */
@Component
public class JmsProducteurImpl implements JmsProducteur {

    @Value("${spring.activemq.demande.submited.queue}")
    String demandeQueue;

    @Value("${spring.activemq.vote.submit.queue}")
    String voteQueue;

    @Value("${spring.activemq.test.queue}")
    String testQueue;

    @Autowired
    JmsTemplate jmsTemplate;

    Logger logger = LoggerFactory.getLogger(JmsProducteurImpl.class);

    @Override
    public void sendDemande(DemandeMessage demande)  {
        try {

            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(demande);
            jmsTemplate.send(demandeQueue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
            logger.info("Message send to queue: " + demandeQueue + ", message: " + jsonObj);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendVote(VoteMessage vote)  {
        try {

            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(vote);
            jmsTemplate.send(voteQueue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
            logger.info("Message send to queue: " + voteQueue + ", message: " + jsonObj);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendTestMessage(TestMessage msg) {
        try {

            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(msg);
            jmsTemplate.send(testQueue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
            logger.info("Message send to queue: " + testQueue + ", message: " + jsonObj);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
