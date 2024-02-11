package ch.hearc.votingservice.remote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class AdminServiceConfiguration {

    @Value("${admin.service.url}")
    String adminServiceUrl;

    /**
     * Retourne une instance d'un client web permettant d'interaggir avec l'api du service admin.service
     * @return un instance de @WebClient configuré pour communiquer ave le service admin-service
     */
    @Bean
    public RestClient adminServiceClient(){
        return RestClient.create(adminServiceUrl);
    }
}
