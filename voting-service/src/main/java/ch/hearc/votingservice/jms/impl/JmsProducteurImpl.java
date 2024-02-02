package ch.hearc.votingservice.jms.impl;

import ch.hearc.votingservice.jms.JmsProducteur;
import ch.hearc.votingservice.jms.models.DemandeMessage;
import ch.hearc.votingservice.jms.models.VoteMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducteurImpl implements JmsProducteur {

    @Value("${spring.activemq.send.demande.queue}")
    String demandeQueue;

    @Value("${spring.activemq.send.vote.queue}")
    String voteQueue;


    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public void sendDemande(DemandeMessage demande)  {
        try {

            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(demande);
            jmsTemplate.send(demandeQueue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
            System.out.println("Message send to queue: " + demandeQueue + ", message: " + jsonObj);
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
            System.out.println("Message send to queue: " + voteQueue + ", message: " + jsonObj);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
