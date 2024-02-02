package ch.hearc.adminservice;

import ch.hearc.adminservice.repository.CampagneRespository;
import ch.hearc.adminservice.repository.ObjetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class AdminServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }


}
