package ch.hearc.adminservice.api.jms.deserializer;

public class JsonDeserialisationException extends Exception{


    public JsonDeserialisationException(String message, Exception e){
        super(message,e);
    }


}
