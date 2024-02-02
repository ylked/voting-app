package ch.hearc.adminservice.api.web;


import ch.hearc.adminservice.service.CampagneService;
import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.Objet;
import ch.hearc.adminservice.service.models.actions.CreateObjetForCampagneResult;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusResult;
import ch.hearc.adminservice.shared.CampagneStatus;
import ch.hearc.adminservice.api.web.models.request.CreateCampagneRequestBody;
import ch.hearc.adminservice.api.web.models.request.CreateObjetRequestBody;
import ch.hearc.adminservice.api.web.models.NoEntityFoundResponseBody;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusAction;
import ch.hearc.adminservice.api.web.models.response.CampagneSimpleResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * Controlleur REST oermettant de manipuler des campagnes de votes et le séléments liés
 */
@Tag(name = "Campagne", description = "Campagne API")
@RestController
@RequestMapping("campagne")
public class CampagneController {

    @Autowired
    CampagneService campagneService;


    /**
     * Création d'une campagne de vote
     * @param body le corsp de la requête contenant les éléments permettant de crééer une campagne
     * @return ResponseEntity contenant le header Location pointatnt vers la ressources
     */
    @Operation(
            summary = "Créatiopn d'une campagne de vote",
            description = "Crééer une campagne de vote avec le status CREATED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")
    })
    @PostMapping
    public ResponseEntity<?> createCampagne(@RequestBody @Valid CreateCampagneRequestBody body){

        Campagne campagne = campagneService.createCampagne(body.getNom());

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(campagne.getIdentifiant()).toUri()).build();

    }

    /**
     * Retourne la liste des campagnes simple, sans les objets liés
     * @return une ResponseEntity avec la liste des campagnes dans le body
     */
    @Operation(
            summary = "Retourne la liste des cmapagnes de vptes",
            description = "Lister les campagnes de votes quelque soit le status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",content = { @Content(schema = @Schema(implementation = CampagneSimpleResponseBody[].class), mediaType = "application/json") })
    })
    @GetMapping
    public ResponseEntity<List<CampagneSimpleResponseBody>> getCampagnes( @Parameter(description = "Filtrer par status", required = false) @RequestParam(required = false, name = "status") Optional<CampagneStatus> status){


        List<Campagne> campagnes = campagneService.getCampagnes(status);

        List<CampagneSimpleResponseBody> campagnesResponseBody
                = campagnes.stream().map(CampagneSimpleResponseBody::mapFromCampagne).toList();

        return ResponseEntity.ok(campagnesResponseBody);
    }

    @Operation(
            summary = "Retourne le détail d'une campagne de vote",
            description = "Retourne le détail d'une campagne de cote via son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "NotFound" ),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCampagneByIdentifiant(@PathVariable("id") String identifiant){

        Optional<Campagne> campagne = campagneService.getCampagneByIdentifiant(identifiant);

        if(campagne.isPresent()){
            return ResponseEntity.ok(campagne.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(identifiant));
        }
    }


    @Operation(
            summary = "Retourne le détail d'un objet d'une campagne de vote",
            description = "Retourne le détail d'un objet d'une campagne de vote via l'identifiant de " +
                    "campagne et l'identifiant de l'objet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "NotFound" ),
    })
    @GetMapping("/{id}/objet/{objetId}")
    public ResponseEntity<?> getObjetByCampagneAndObjetIdentifiant(@PathVariable("id") String idCampagne,@PathVariable("objetId") String idObjet ){

        //check if campagne exist
        Optional<Campagne> campagne = campagneService.getCampagneByIdentifiant(idCampagne);

        if(campagne.isPresent()){
            Optional<Objet> optionnalObjet = campagneService.getObjetByIdentifiant(idObjet);

            if(optionnalObjet.isPresent()){
                return ResponseEntity.ok(optionnalObjet.get());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(idObjet));
            }

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(idCampagne));
        }
    }

    @Operation(
            summary = "Modifie le status d'une campagne de vote",
            description = "Modifie le status d'une campagne de vote, dans cette séquence: CREATED -> OPENED -> CLOSED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "NotFound, si identifiant retourne rien" ),
            @ApiResponse(responseCode = "400", description = "BadRequest, si erreur" ),
    })
    @PutMapping("/{id}")
    public ResponseEntity updateCampagneStatus(@PathVariable("id") String identifiant, @Parameter(description = "L'action à appliquer pour changer le status",required = true) @RequestParam(value = "action",required = true) UpdateCampagneStatusAction action){

        Optional<Campagne> optionalCampagne = campagneService.getCampagneByIdentifiant(identifiant);

        if(optionalCampagne.isPresent()){
            UpdateCampagneStatusResult resultAction = campagneService.updateCampganeStatus(optionalCampagne.get(),action);

            if(resultAction.isActionOk()){
                return ResponseEntity.ok(resultAction.getCampagne());
            }else{
                return ResponseEntity.badRequest().body(resultAction);
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(identifiant));
        }

    }

    @Operation(
            summary = "Ajoute un objet à une campagne de vote",
            description = "Ajoute un objet à une campagne de vote, la campagne doit être dans le status CREATED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "NotFound, si identifiant retourne rien" ),
            @ApiResponse(responseCode = "400", description = "BadRequest, si erreur" ),
    })
    @PostMapping("/{id}/objet")
    public ResponseEntity<?> addObjetsToCampagne(@RequestBody @Valid CreateObjetRequestBody body, @PathVariable("id") String identifiant){

        //check if campgane exist by identifiant
        Optional<Campagne> campagne = campagneService.getCampagneByIdentifiant(identifiant);

        if(campagne.isPresent()){
            CreateObjetForCampagneResult actionResult = campagneService.createObjetForCampagne(campagne.get(),body.getNom());

            if(actionResult.isActionOk()){
                return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(actionResult.getObjet().getIdentifiant()).toUri()).build();
            }else{

                return ResponseEntity.badRequest().body(actionResult);
            }


        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(identifiant));
        }
    }

    /**
     * Méthode gérant les exceptions de validation issues de l'annotation @Valid
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
