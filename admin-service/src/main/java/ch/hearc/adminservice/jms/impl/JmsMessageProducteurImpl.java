package ch.hearc.adminservice.jms.impl;


import ch.hearc.adminservice.jms.JmsMessageProducteur;
import ch.hearc.adminservice.jms.models.AutorisationMessage;
import ch.hearc.adminservice.jms.models.RefusAutorisationMessaage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsMessageProducteurImpl implements JmsMessageProducteur {

    @Value("${spring.activemq.send.autorisation.queue}")
    String autorisationQueue;

    @Value("${spring.activemq.send.deniedautorisation.queue}")
    String deniedAuthorisationQueue;

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

            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(autorisation);
            jmsTemplate.send(autorisationQueue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });

            System.out.println("Message send to queue: " + autorisationQueue + ", message: " + jsonObj);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendDeniedAutorisation(RefusAutorisationMessaage refusAutorisation) throws JsonProcessingException {
        try {

            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(refusAutorisation);
            jmsTemplate.send(deniedAuthorisationQueue, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });

            System.out.println("Message send to queue: " + deniedAuthorisationQueue + ", message: " + jsonObj);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
