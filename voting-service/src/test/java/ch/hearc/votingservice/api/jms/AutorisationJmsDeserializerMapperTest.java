package ch.hearc.votingservice.api.jms;

import ch.hearc.votingservice.api.jms.impl.deserializer.AutorisationJmsDeserializerMapper;
import ch.hearc.votingservice.api.jms.impl.deserializer.JsonDeserialisationException;
import ch.hearc.votingservice.api.jms.models.AutorisationDto;
import ch.hearc.votingservice.service.models.Demande;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AutorisationJmsDeserializerMapperTest {

    @Autowired
    AutorisationJmsDeserializerMapper autorisationJmsDeserializerMapper;
    @Test
    public void testValidationFailed(){

        //Tests divers problèmes de validations
        Exception exception = assertThrows(JsonDeserialisationException.class, () -> {
            AutorisationDto autorisationDto = autorisationJmsDeserializerMapper.mapFromJson("{\"identifiant\":\"id\"}");
        });

        exception = assertThrows(JsonDeserialisationException.class, () -> {
            AutorisationDto autorisationDto = autorisationJmsDeserializerMapper.mapFromJson("{\"identifiant\":\"1234567\"}");
        });

        exception = assertThrows(JsonDeserialisationException.class, () -> {
            AutorisationDto autorisationDto = autorisationJmsDeserializerMapper.mapFromJson("{\"identifiant\":\"1234567\"}");
        });

    }

    //@Test
    public void testValidationOk(){

        String json = "{\"identifiant\":\"123456\",\"campagneId\":\"123456\",\"demandeId\":\"123456\",\"autorisationCode\":\"123456\"}";
        AutorisationDto autorisationDto = null;
        try {
            autorisationDto = autorisationJmsDeserializerMapper.mapFromJson(json);
            assertNotNull(autorisationDto);
            assertNotNull(autorisationDto.getAutorisationCode());
            assertNotNull(autorisationDto.getCampagneId());
            assertNotNull(autorisationDto.getDemandeId());
            //assertNotNull(autorisationDto.getIdentifiant());

        } catch (JsonDeserialisationException e) {
            throw new RuntimeException(e);
        }



    }
}