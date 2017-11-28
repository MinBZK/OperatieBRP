/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandelingGedeblokkeerdeRegel;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.repository.kern.AdministratieveHandelingRepository;
import nl.bzk.brp.beheer.webapp.view.ActieView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdministratieveHandelingController voor inzage administratieve handelingen.
 */
@RestController
@RequestMapping(value = ControllerConstants.ADMINISTRATIEVE_HANDELING_URI)
public final class AdministratieveHandelingController extends AbstractReadonlyController<AdministratieveHandeling, Long> {

    private static final String ID = "id";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_FIELD = "datumTijdRegistratie";
    private static final String PARAMETER_TIJDSTIP_LEVERING_FIELD = "datumTijdLevering";

    private static final List<String> SORTERINGEN = Arrays.asList(PARAMETER_TIJDSTIP_REGISTRATIE_FIELD, PARAMETER_TIJDSTIP_LEVERING_FIELD);

    private final ActieController actieController;
    private final GedeblokkeerdeMeldingController gedeblokkeerdeMeldingController;

    /**
     * Constructor voor AdministratieveHandelingController.
     * @param repository repo
     * @param actieController actiecontroller voor onderliggende acties
     * @param gedeblokkeerdeMeldingController controller voor onderliggende gedeblokkeerde meldingen
     */
    @Inject
    public AdministratieveHandelingController(final AdministratieveHandelingRepository repository, final ActieController actieController,
                                              final GedeblokkeerdeMeldingController gedeblokkeerdeMeldingController) {
        super(repository,
                Arrays.asList(
                        AdministratieveHandelingFilterFactory.getFilterSoort(),
                        AdministratieveHandelingFilterFactory.getFilterPartij(),
                        AdministratieveHandelingFilterFactory.getFilterPartijCode(),
                        AdministratieveHandelingFilterFactory.getFilterPartijNaam(),
                        AdministratieveHandelingFilterFactory.getFilterTijdstipRegistratieBegin(),
                        AdministratieveHandelingFilterFactory.getFilterTijdstipRegistratieEinde(),
                        AdministratieveHandelingFilterFactory.getFilterTijdstipLeveringBegin(),
                        AdministratieveHandelingFilterFactory.getFilterTijdstipLeveringEinde(),
                        AdministratieveHandelingFilterFactory.getFilterBurgerServiceNummer(),
                        AdministratieveHandelingFilterFactory.getFilterANummer()),
                SORTERINGEN,
                false);
        this.actieController = actieController;
        this.gedeblokkeerdeMeldingController = gedeblokkeerdeMeldingController;
    }

    /**
     * Haal een specifiek actie op. Hier een ActieView object omdat in dit object ook alle gerelateerde data moet
     * zitten.
     * @param id id
     * @param actieId actieId
     * @return item
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/acties/{aid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ActieView getActie(@PathVariable(ID) final Long id, @PathVariable("aid") final Long actieId) throws ErrorHandler.NotFoundException {
        return actieController.get(actieId);
    }

    /**
     * Haal een lijst van items op.
     * @param id id van actie
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/acties", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ActieView> listActie(
            @PathVariable(value = ID) final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) @SortDefault(sort = "datumTijdRegistratie", direction = Sort.Direction.ASC) final Pageable pageable) {
        parameters.put(ActieController.PARAMETER_ADMINISTRATIEVE_HANDELING, id);
        return actieController.list(parameters, pageable);
    }

    /**
     * Haal een specifiek AdministratieveHandelingGedeblokkeerdeMelding op.
     * @param id id
     * @param geblokeerdeMeldingId geblokeerdeMeldingId
     * @return item
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/gedeblokkeerdemeldingen/{melid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AdministratieveHandelingGedeblokkeerdeRegel getAdministratieveHandelingGedeblokkeerdeMelding(
            @PathVariable(ID) final Long id,
            @PathVariable("melid") final Long geblokeerdeMeldingId) throws ErrorHandler.NotFoundException {
        return gedeblokkeerdeMeldingController.get(geblokeerdeMeldingId);
    }

    /**
     * Haal een lijst van items op.
     * @param id id van actie
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/gedeblokkeerdemeldingen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<AdministratieveHandelingGedeblokkeerdeRegel> listAdministratieveHandelingGedeblokkeerdeMeldingen(
            @PathVariable(value = ID) final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) final Pageable pageable) {
        parameters.put(ActieController.PARAMETER_ADMINISTRATIEVE_HANDELING, id);
        return gedeblokkeerdeMeldingController.list(parameters, pageable);
    }
}
