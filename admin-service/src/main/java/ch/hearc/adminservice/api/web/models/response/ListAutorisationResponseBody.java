package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.api.web.models.AutorisationBody;
import ch.hearc.adminservice.api.web.models.CampagneBody;
import ch.hearc.adminservice.service.models.Autorisation;
import ch.hearc.adminservice.service.models.Demande;

import java.util.List;

public class ListAutorisationResponseBody {

    private List<AutorisationBody> autorisations;
    private Integer total;

    public List<AutorisationBody> getAutorisations() {
        return autorisations;
    }

    public Integer getTotal() {
        return total;
    }

    private  ListAutorisationResponseBody(int size, List<AutorisationBody> autorisations) {
        this.total = size;
        this.autorisations = autorisations;
    }

    public static ListAutorisationResponseBody mapFromListAutorisation(List<Autorisation> autorisations) {


        List<AutorisationBody> autorisationsBody = autorisations.stream().map(autorisation -> {
            return new AutorisationBody(autorisation.getAutorisationCode(),autorisation.getCampagneId());
        }).toList();

        return new ListAutorisationResponseBody(autorisationsBody.size(),autorisationsBody);
    }
}
