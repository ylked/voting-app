package ch.hearc.votingservice.api.web;

import ch.hearc.votingservice.api.web.models.request.CreateDemandeRequestBody;
import ch.hearc.votingservice.api.web.models.response.CreateDemandeResponseBody;
import ch.hearc.votingservice.api.web.models.response.DemandeResponseBody;
import ch.hearc.votingservice.api.web.models.response.NoEntityFoundResponseBody;
import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import ch.hearc.votingservice.shared.DemandeStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlleur gérant les demande d'autorisations de votes
 */
@RestController
@RequestMapping("demandes")
public class DemandesController extends AbstractValidationHandler{

    @Autowired
    AutorisationService autorisationService;
    /**
     * Création d'une demande d'autorisation de vote pour une campagne
     * @return une autorisation de vote
     */
    @PostMapping()
    public ResponseEntity<?> demandeAutorisationPourCampagne(@RequestBody @Valid CreateDemandeRequestBody demandeBody){

        //Converion du body en demande métier
        Demande demande = CreateDemandeRequestBody.toDemande(demandeBody);

        try {
            CreateDemandeAutorisationResult result = autorisationService.createDemandeAutorisation(demande);

            if(result.isActionOk()){
                return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(result.getDemande().getIdentifiant()).toUri())
                        .body(new CreateDemandeResponseBody("Demande successfully created",demande.getCampagneId(),demande.getIdentifiant()));
            }else{
                return ResponseEntity.badRequest().body(result);
            }


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDemandeByIdentifiant(@PathVariable("id") String identifiant){

        Optional<Demande> demande = autorisationService.getDemandeByIdentifiant(identifiant);

        if(demande.isPresent()){
            return ResponseEntity.ok(DemandeResponseBody.mapFromDemande(demande.get()));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(identifiant));
        }
    }

    @GetMapping()
    public ResponseEntity<List<Demande>> getAllDemandes(@RequestParam(required = false, name = "status") Optional<DemandeStatus> status){

        List<Demande> demandes = autorisationService.findAllDemandes(status);

        return ResponseEntity.ok(demandes);
    }

}
