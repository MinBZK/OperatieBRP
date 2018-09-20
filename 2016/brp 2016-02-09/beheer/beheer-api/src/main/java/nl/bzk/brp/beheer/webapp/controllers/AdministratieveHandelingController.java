/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.DateValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EnumValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.kern.ActieViewRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.AdministratieveHandelingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.beheer.view.ActieView;
import nl.bzk.brp.model.beheer.view.Acties;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingGedeblokkeerdeMeldingModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdministratieveHandelingController extends AbstractReadonlyController<AdministratieveHandelingModel, Long> {

    private static final String SOORT = "soort";
    private static final String PARAMETER_PARTIJ = "partij";
    private static final String PARAMETER_PARTIJ_CODE = "partijCode";
    private static final String PARAMETER_PARTIJ_FIELD = "partij.waarde.iD";
    private static final String PARAMETER_PARTIJ_CODE_FIELD = "partij.waarde.code.waarde";
    private static final String PARAMETER_PARTIJ_NAAM = "partijNaam";
    private static final String PARAMETER_PARTIJ_NAAM_FIELD = "partij.waarde.naam.waarde";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_BEGIN = "tijdstipRegistratieBegin";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_EINDE = "tijdstipRegistratieEinde";
    private static final String PARAMETER_TIJDSTIP_REGISTRATIE_FIELD = "tijdstipRegistratie";
    private static final String PARAMETER_TIJDSTIP_LEVERING_BEGIN = "tijdstipLeveringBegin";
    private static final String PARAMETER_TIJDSTIP_LEVERING_EINDE = "tijdstipLeveringEinde";
    private static final String PARAMETER_TIJDSTIP_LEVERING_FIELD = "levering.tijdstipLevering";

    private static final String PARAMETER_BSN = "bsn";
    private static final String PARAMETER_BSN_FIELD = "hisPersoonAfgeleidAdministratief.persoon.identificatienummers.burgerservicenummer";
    private static final String PARAMETER_ANUMMER = "anummer";
    private static final String PARAMETER_ANUMMER_FIELD = "hisPersoonAfgeleidAdministratief.persoon.identificatienummers.administratienummer";

    private static final Filter<?> FILTER_SOORT = new Filter<>(SOORT, new AttribuutValueAdapter<>(new EnumValueAdapter<>(
            SoortAdministratieveHandeling.class), SoortAdministratieveHandelingAttribuut.class), new EqualPredicateBuilderFactory(SOORT));
    private static final Filter<?> FILTER_PARTIJ = new Filter<>(PARAMETER_PARTIJ, new ShortValueAdapter(), new EqualPredicateBuilderFactory(
            PARAMETER_PARTIJ_FIELD));
    private static final Filter<?> FILTER_PARTIJ_CODE = new Filter<>(PARAMETER_PARTIJ_CODE, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(
            PARAMETER_PARTIJ_CODE_FIELD));
    private static final Filter<?> FILTER_PARTIJ_NAAM = new Filter<>(PARAMETER_PARTIJ_NAAM, new StringValueAdapter(), new LikePredicateBuilderFactory(
            PARAMETER_PARTIJ_NAAM_FIELD));
    private static final Filter<?> FILTER_TIJDSTIP_REGISTRATIE_BEGIN = new Filter<>(PARAMETER_TIJDSTIP_REGISTRATIE_BEGIN, new AttribuutValueAdapter<>(
            new DateValueAdapter(),
            DatumTijdAttribuut.class), new GreaterOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(PARAMETER_TIJDSTIP_REGISTRATIE_FIELD));
    private static final Filter<?> FILTER_TIJDSTIP_REGISTRATIE_EINDE = new Filter<>(PARAMETER_TIJDSTIP_REGISTRATIE_EINDE, new AttribuutValueAdapter<>(
            new DateValueAdapter(),
            DatumTijdAttribuut.class), new LessOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(PARAMETER_TIJDSTIP_REGISTRATIE_FIELD));
    private static final Filter<?> FILTER_TIJDSTIP_LEVERING_BEGIN = new Filter<>(PARAMETER_TIJDSTIP_LEVERING_BEGIN, new AttribuutValueAdapter<>(
            new DateValueAdapter(),
            DatumTijdAttribuut.class), new GreaterOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(PARAMETER_TIJDSTIP_LEVERING_FIELD));
    private static final Filter<?> FILTER_TIJDSTIP_LEVERING_EINDE = new Filter<>(PARAMETER_TIJDSTIP_LEVERING_EINDE, new AttribuutValueAdapter<>(
            new DateValueAdapter(),
            DatumTijdAttribuut.class), new LessOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(PARAMETER_TIJDSTIP_LEVERING_FIELD));
    private static final Filter<?> FILTER_BSN = new Filter<>(PARAMETER_BSN, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(
            PARAMETER_BSN_FIELD));
    private static final Filter<?> FILTER_ANUMMER = new Filter<>(PARAMETER_ANUMMER, new LongValueAdapter(), new EqualPredicateBuilderFactory(
            PARAMETER_ANUMMER_FIELD));

    private static final List<String> SORTERINGEN = Arrays.asList(PARAMETER_TIJDSTIP_REGISTRATIE_FIELD, PARAMETER_TIJDSTIP_LEVERING_FIELD);

    @Autowired
    private ReadonlyController<ActieModel, Long> actieController;

    @Autowired
    private ActieViewRepository actieViewRepository;

    @Autowired
    private ReadonlyController<AdministratieveHandelingGedeblokkeerdeMeldingModel, Long> gedeblokkeerdeMeldingModelController;

    /**
     * Constructor voor AdministratieveHandelingController.
     *
     * @param repository repo
     */
    @Autowired
    public AdministratieveHandelingController(final AdministratieveHandelingRepository repository) {
        super(repository, Arrays.asList(
                FILTER_SOORT,
                FILTER_PARTIJ,
                FILTER_PARTIJ_CODE,
                FILTER_PARTIJ_NAAM,
                FILTER_TIJDSTIP_REGISTRATIE_BEGIN,
                FILTER_TIJDSTIP_REGISTRATIE_EINDE,
                FILTER_TIJDSTIP_LEVERING_BEGIN,
                FILTER_TIJDSTIP_LEVERING_EINDE,
                FILTER_BSN,
                FILTER_ANUMMER), SORTERINGEN, false);
    }

    /**
     * Haal een specifiek actie op. Hier een ActieView object omdat in dit object ook alle gerelateerde data moet
     * zitten.
     *
     * @param id      id
     * @param actieId actieId
     * @return item
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/acties/{aid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final ActieView getActie(@PathVariable("id") final Long id, @PathVariable("aid") final Long actieId) throws ErrorHandler.NotFoundException {
        return Acties.asActieView(actieViewRepository.findOne(actieId));
    }

    /**
     * Haal een lijst van items op.
     *
     * @param id         id van actie
     * @param parameters request parameters
     * @param pageable   paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/acties", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<ActieModel> listActie(
            @PathVariable(value = "id") final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) @SortDefault(sort = "tijdstipRegistratie", direction = Sort.Direction.ASC) final Pageable pageable)
    {
        parameters.put(ActieController.PARAMETER_ADMINISTRATIEVE_HANDELING, id);
        return actieController.list(parameters, pageable);
    }

    /**
     * Haal een specifiek AdministratieveHandelingGedeblokkeerdeMelding op.
     *
     * @param id                   id
     * @param geblokeerdeMeldingId geblokeerdeMeldingId
     * @return item
     * @throws ErrorHandler.NotFoundException wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/gedeblokkeerdemeldingen/{melid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final AdministratieveHandelingGedeblokkeerdeMeldingModel getAdministratieveHandelingGedeblokkeerdeMelding(
            @PathVariable("id") final Long id,
            @PathVariable("melid") final Long geblokeerdeMeldingId)
            throws ErrorHandler.NotFoundException
    {
        return gedeblokkeerdeMeldingModelController.get(geblokeerdeMeldingId);
    }

    /**
     * Haal een lijst van items op.
     *
     * @param id         id van actie
     * @param parameters request parameters
     * @param pageable   paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/gedeblokkeerdemeldingen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<AdministratieveHandelingGedeblokkeerdeMeldingModel> listAdministratieveHandelingGedeblokkeerdeMeldingen(
            @PathVariable(value = "id") final String id,
            @RequestParam final Map<String, String> parameters,
            @PageableDefault(size = 10) final Pageable pageable)
    {
        parameters.put(ActieController.PARAMETER_ADMINISTRATIEVE_HANDELING, id);
        return gedeblokkeerdeMeldingModelController.list(parameters, pageable);
    }
}
