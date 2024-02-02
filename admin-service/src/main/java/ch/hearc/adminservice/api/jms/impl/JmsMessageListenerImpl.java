package ch.hearc.adminservice.api.jms.impl;


import ch.hearc.adminservice.api.jms.JmsMessageListener;
//import ch.hearc.adminservice.service.AutorisationService;
import ch.hearc.adminservice.service.AutorisationService;
import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.actions.ReceptionnerDemandeResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class JmsMessageListenerImpl implements JmsMessageListener {

    @Autowired
    AutorisationService autorisationService;
    @Autowired
    ObjectMapper mapper;

    /**
     * Listener à l'écoute des demandes d'autorisations
     * Une demande d'autorisation permet de recevoir une autorisation de vote.
     * Les demande sont stockées en base de donnée
     * @param jsonMessage
     * @throws JMSException
     */
    @Override
    @JmsListener(destination = "send-demande")
    public void listenDemandes(final TextMessage jsonMessage) throws JMSException, JsonProcessingException {

        String messageData = null;

        System.out.println("Message received from queue: send-demande");

        if(jsonMessage != null) {
            messageData = jsonMessage.getText();

            System.out.println("Message received from queue: send-demande, message: " + messageData);
            Demande demande = mapper.readValue(messageData, Demande.class);

            ReceptionnerDemandeResult result = autorisationService.receptionnerDemande(demande);

            System.out.println(result.getMessage());
            System.out.println(result.getSuccess());

        }
    }
}
