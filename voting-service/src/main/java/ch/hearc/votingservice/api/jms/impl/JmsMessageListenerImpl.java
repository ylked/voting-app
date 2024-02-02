package ch.hearc.votingservice.api.jms.impl;

import ch.hearc.votingservice.api.jms.JmsMessageListener;
import ch.hearc.votingservice.api.jms.impl.deserializer.AutorisationJmsDeserializerMapper;
import ch.hearc.votingservice.api.jms.impl.deserializer.JsonDeserialisationException;
import ch.hearc.votingservice.api.jms.models.AutorisationDto;
import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.service.models.Autorisation;
import ch.hearc.votingservice.service.models.actions.ValidateAutorisationResult;
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
    AutorisationJmsDeserializerMapper mapper;

    @Autowired
    AutorisationService autorisationService;

    @JmsListener(destination = "denied-autorisation")
    @Override
    public void listenRefusDemande(TextMessage jsonMessage) throws JMSException, JsonProcessingException {

    }


    /**
     * Listener sur la queue des demandes d'autorisations
     * Une fois réceptionné une dmande d'autorisation sera convertie en objet votant
     * @param jsonMessage le message jms
     * @throws JMSException
     * @throws JsonProcessingException
     */
    @JmsListener(destination = "send-autorisation")
    @Override
    public void listenAutorisation(TextMessage jsonMessage) throws JMSException, JsonProcessingException {

        String messageData = null;

        System.out.println("Message received from queue: send-autorisation");

        if(jsonMessage != null) {
            messageData = jsonMessage.getText();

            System.out.println("Message received from queue: send-autorisation, message: " + messageData);
            //Conversion en autorisation
            try{
                AutorisationDto autorisationDto = mapper.mapFromJson(messageData);
                Autorisation autorisation = AutorisationDto.toAutorisation(autorisationDto);
                ValidateAutorisationResult result = autorisationService.validateAutorisation(autorisation);

            }catch (JsonDeserialisationException e){
                e.printStackTrace();
            }


        }
    }
}
