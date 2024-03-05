package ch.hearc.votingservice.api.jms.impl;

import ch.hearc.votingservice.api.jms.JmsMessageListener;
import ch.hearc.votingservice.api.jms.impl.deserializer.AutorisationJmsDeserializerMapper;
import ch.hearc.votingservice.api.jms.impl.deserializer.JsonDeserialisationException;
import ch.hearc.votingservice.api.jms.impl.deserializer.RejectedAutorisationDto;
import ch.hearc.votingservice.api.jms.models.AutorisationDto;
import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.service.models.Autorisation;
import ch.hearc.votingservice.service.models.actions.ValidateAutorisationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsMessageListenerImpl implements JmsMessageListener {

    Logger logger = LoggerFactory.getLogger(JmsMessageListenerImpl.class);

    @Autowired
    AutorisationJmsDeserializerMapper mapper;

    @Autowired
    AutorisationService autorisationService;

    @JmsListener(destination = "${spring.activemq.demande.denied.queue}")
    @Override
    public void listenRefusDemande(TextMessage jsonMessage) throws JMSException, JsonProcessingException {

        logger.info("listenRefusDemande jms listener triggered");
        String messageData = null;


        if(jsonMessage != null) {
            messageData = jsonMessage.getText();

            logger.info("Listen refus message received from queue:");
            logger.info(messageData);

            //Conversion en refus
            try{


                RejectedAutorisationDto rejectedAutorisationDto = mapper.mapRejectedFromJson(messageData);

                Boolean isRejected = autorisationService.rejectDemandeAutorisation(rejectedAutorisationDto.getDemandeIdentifiant(),rejectedAutorisationDto.getMessage());


                logger.info("Rejected demande process done, result: " + (isRejected ? "Ok" : "Ko"));
            }catch (JsonDeserialisationException e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }


        }

    }


    /**
     * Listener sur la queue des demandes d'autorisations
     * Une fois réceptionné une dmande d'autorisation sera convertie en objet votant
     * @param jsonMessage le message jms
     * @throws JMSException
     * @throws JsonProcessingException
     */
    @JmsListener(destination = "${spring.activemq.demande.accepted.queue}")
    @Override
    public void listenAutorisation(TextMessage jsonMessage) throws JMSException, JsonProcessingException {

        logger.info("ListenAutorisation jms listener triggered");
        String messageData = null;


        if(jsonMessage != null) {
            messageData = jsonMessage.getText();

            logger.info("Listen autorisations message received from queue:");
            logger.info(messageData);

            //Conversion en autorisation
            try{
                AutorisationDto autorisationDto = mapper.mapFromJson(messageData);
                Autorisation autorisation = AutorisationDto.toAutorisation(autorisationDto);
                ValidateAutorisationResult result = autorisationService.validateAutorisation(autorisation);


                logger.info("Autorisation process done, result: " + result.getMessage());
            }catch (JsonDeserialisationException e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }


        }
    }
}
