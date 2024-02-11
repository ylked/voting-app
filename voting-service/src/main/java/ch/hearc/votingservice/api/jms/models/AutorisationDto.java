package ch.hearc.votingservice.api.jms.models;

import ch.hearc.votingservice.service.models.Autorisation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfert Object) utilislé uniquement pour mapper des messages json
 * en objet Autorisation
 */
public class AutorisationDto {


    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String autorisationCode;
    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String demandeId;
    @NotEmpty
    @NotNull
    @Size(min = 5)
    private String campagneId;



    public String getAutorisationCode() {
        return autorisationCode;
    }


    public String getDemandeId() {
        return demandeId;
    }


    public String getCampagneId() {
        return campagneId;
    }

    public static Autorisation toAutorisation(AutorisationDto autorisationDto){
        return Autorisation.nouvelleAutorisation(
                autorisationDto.autorisationCode,
                autorisationDto.demandeId,
                autorisationDto.campagneId);
    }

    public AutorisationDto(){}

}
