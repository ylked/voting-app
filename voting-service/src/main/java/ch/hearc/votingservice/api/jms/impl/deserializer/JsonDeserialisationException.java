package ch.hearc.votingservice.api.jms.impl.deserializer;

public class JsonDeserialisationException extends Exception{


    public JsonDeserialisationException(String message, Exception e){
        super(message,e);
    }


}
