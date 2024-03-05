package ch.hearc.votingservice.api.jms.impl.deserializer;

import ch.hearc.votingservice.api.jms.models.AutorisationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AutorisationJmsDeserializerMapper {


    private static ObjectMapper getObjectMapperWithValidation(){
        SimpleModule validationModule = new SimpleModule();
        validationModule.setDeserializerModifier(new JmsDtoWithValidationModifierDeserializer());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(validationModule);
        return mapper;
    }
    public AutorisationDto mapFromJson(String json) throws  JsonDeserialisationException {

        try{
            ObjectMapper mapper = getObjectMapperWithValidation();
            return mapper.readValue(json,AutorisationDto.class);
        }catch(ConstraintViolationException | JsonProcessingException e){
            throw new JsonDeserialisationException(e.getMessage(),e);
        }


    }

    public RejectedAutorisationDto mapRejectedFromJson(String json) throws  JsonDeserialisationException {

        try{
            ObjectMapper mapper = getObjectMapperWithValidation();
            return mapper.readValue(json,RejectedAutorisationDto.class);
        }catch(ConstraintViolationException | JsonProcessingException e){
            throw new JsonDeserialisationException(e.getMessage(),e);
        }


    }
}
