package ch.hearc.adminservice.api.web;

import ch.hearc.adminservice.api.web.models.response.AutorisationDemandeResponseBody;
import ch.hearc.adminservice.api.web.models.response.DemandeResponseBody;
import ch.hearc.adminservice.service.AutorisationService;
import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.actions.ValidateDemandeResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
@Tag(name = "Autorisation", description = "Autorisation API")
@RestController
@RequestMapping("autorisation")
public class AutorisationController {

    @Autowired
    AutorisationService autorisationService;

    @GetMapping( value = "/demandes")
    @Operation(
            summary = "Récupération des demandes d'autorisations",
            description = "Récupére les demandes d'autorisations en cours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    public ResponseEntity<List<DemandeResponseBody>> getDemandes(){

        return ResponseEntity.ok(autorisationService.getDemandes()
                .stream()
                .map(DemandeResponseBody::fromDemande).toList());
    }

    @PostMapping(value = "/demandes/{id}")
    @Operation(
            summary = "Autorisation d'une demande en cours",
            description = "Autorise une demande et la convertit en autorisation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Demande traitée"),
            @ApiResponse(responseCode = "400", description = "Erreur durant le traitement"),
    })
    public ResponseEntity<?> autoriseDemande(@PathVariable String id) throws JsonProcessingException {

        ValidateDemandeResult actionResult = autorisationService.autoriseDemande(id);

        if(actionResult.isSuccess()){
            AutorisationDemandeResponseBody responseBody = new AutorisationDemandeResponseBody(
                    actionResult.getAutorisation().getAutorisationCode(),
                    actionResult.getAutorisation().getCampagneId());

            return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(actionResult.getDemande().getIdentifiant()).toUri()).body(responseBody);

        }else{
            return ResponseEntity.badRequest().body(actionResult);
        }
    }


}
