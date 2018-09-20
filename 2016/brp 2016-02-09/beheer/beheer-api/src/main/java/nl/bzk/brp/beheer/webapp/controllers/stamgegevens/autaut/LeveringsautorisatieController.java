/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.ReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EnumValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.JaNeeAttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.NeeAttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.ToegangLeveringsautorisatie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * LeveringsautorisatieController voor beheer abonnement.
 */
@RestController
@RequestMapping(value = ControllerConstants.LEVERINGSAUTORISATIE_URI)
@SuppressWarnings("checkstyle:classfanoutcomplexity")
public class LeveringsautorisatieController extends AbstractReadWriteController<Leveringsautorisatie, Integer> {

    private static final String NAAM = "naam";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";

    private static final Filter<?> FILTER_NAAM = new Filter<>("filterNaam", new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        NaamEnumeratiewaardeAttribuut.class), new LikePredicateBuilderFactory(NAAM));
    private static final Filter<?> FILTER_PROTOCOLLERING = new Filter<>(
        "filterProtocollering",
        new EnumValueAdapter<>(Protocolleringsniveau.class),
        new EqualPredicateBuilderFactory("protocolleringsniveau"));

    private static final Filter<?> FILTER_DATUM_INGANG = new Filter<>("filterDatumIngang", new AttribuutValueAdapter<>(
        new IntegerValueAdapter(),
        DatumEvtDeelsOnbekendAttribuut.class), new EqualPredicateBuilderFactory(DATUM_INGANG));
    private static final Filter<?> FILTER_DATUM_EINDE = new Filter<>("filterDatumEinde", new AttribuutValueAdapter<>(
        new IntegerValueAdapter(),
        DatumEvtDeelsOnbekendAttribuut.class), new EqualPredicateBuilderFactory(DATUM_EINDE));
    //private static final Filter<?> FILTER_TOESTAND = null;
    // new Filter<>(
    // "filterToestand",
    // new EnumValueAdapter<>(Toestand.class),
    // new EqualPredicateBuilderFactory("toestand"));
    private static final Filter<?> FILTER_ALIAS = new Filter<>("filterAlias", new JaNeeAttribuutValueAdapter(), new EqualPredicateBuilderFactory(
        "indicatieAliasSoortAdministratieveHandelingLeveren"));
    private static final Filter<?> FILTER_IND_POPBEPERK_VOLLEDIG = new Filter<>(
        "filterIndPopbeperkVolledig",
        new NeeAttribuutValueAdapter(),
        new EqualPredicateBuilderFactory("indicatiePopulatiebeperkingVolledigGeconverteerd"));

    private static final Filter<?> FILTER_TOEGANG_PARTIJ_ID = new Filter<>(
        "filterToegangPartijId",
        new ShortValueAdapter(),
        new EqualPredicateBuilderFactory("toegangen.partij.iD"));
    private static final Filter<?> FILTER_TOEGANG_PARTIJ_CODE = new Filter<>("filterToegangPartijCode", new AttribuutValueAdapter<>(
        new IntegerValueAdapter(),
        PartijCodeAttribuut.class), new EqualPredicateBuilderFactory("toegangen.partij.code"));
    private static final Filter<?> FILTER_TOEGANG_PARTIJ_NAAM = new Filter<>("filterToegangPartijNaam", new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        NaamEnumeratiewaardeAttribuut.class), new EqualPredicateBuilderFactory("toegangen.partij.naam"));
    //private static final Filter<?> FILTER_DIENST_CATALOGUS_OPTIE = null;
    // new Filter<>(
    // "filterDienstCatalogusOptie",
    // new EnumValueAdapter<>(CatalogusOptie.class),
    // new EqualPredicateBuilderFactory("diensten.catalogusOptie"));
    private static final List<String> SORTERINGEN = Arrays.asList(NAAM, DATUM_INGANG, DATUM_EINDE);

    @Autowired
    private ReadWriteController<ToegangLeveringsautorisatie, Integer> toegangLeveringsautorisatieController;

    /**
     * Constructor voor LeveringsautorisatieController.
     *
     * @param repository
     *            repo
     */
    @Autowired
    public LeveringsautorisatieController(final LeveringsautorisatieRepository repository) {
        super(repository, Arrays.asList(FILTER_NAAM, FILTER_PROTOCOLLERING, FILTER_DATUM_INGANG, FILTER_DATUM_EINDE,
        // FILTER_TOESTAND,
            FILTER_ALIAS,
            FILTER_IND_POPBEPERK_VOLLEDIG,
            FILTER_TOEGANG_PARTIJ_ID,
            FILTER_TOEGANG_PARTIJ_CODE,
            FILTER_TOEGANG_PARTIJ_NAAM
        // FILTER_DIENST_CATALOGUS_OPTIE
        ), Arrays.<HistorieVerwerker<Leveringsautorisatie, ?>>asList(new HisLeveringsautorisatieVerwerker()), SORTERINGEN);
    }

    // /**
    // * Haal een specifiek item op.
    // *
    // * @param id id
    // * @param dienstId diesntId
    // * @return item
    // * @throws NotFoundException wanneer het item niet gevonden kan worden
    // */
    // @RequestMapping(value = "/{id}/diensten/{did}", method = RequestMethod.GET)
    // @ResponseStatus(HttpStatus.OK)
    // public final Dienst getDienst(@PathVariable("id") final Integer id, @PathVariable("did") final Integer dienstId)
    // throws ErrorHandler.NotFoundException {
    // return dienstController.get(dienstId);
    // }
    //
    // /**
    // * Haal een lijst van items op.
    // *
    // * @param id id van abonnement
    // * @param parameters request parameters
    // * @param pageable paginering
    // * @return lijst van item (inclusief paginering en sortering)
    // */
    // @RequestMapping(value = "/{id}/diensten", method = RequestMethod.GET)
    // @ResponseStatus(HttpStatus.OK)
    // public final Page<Dienst> listDienst(
    // @PathVariable(value = "id") final String id,
    // @RequestParam final Map<String, String> parameters,
    // @PageableDefault(size = 10) final Pageable pageable)
    // {
    // parameters.put(DienstController.PARAMETER_FILTER_ABONNEMENT, id);
    // return dienstController.list(parameters, pageable);
    // }
    //
    // /**
    // * Slaat een gewijzigd item op.
    // *
    // * @param item item
    // * @return item
    // * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
    // */
    // @RequestMapping(value = "/{id}/diensten", method = RequestMethod.POST)
    // @ResponseStatus(HttpStatus.OK)
    // public final Dienst saveDienst(@RequestBody final Dienst item) throws NotFoundException {
    // return dienstController.save(item);
    // }
    //
    /**
     * Haal een specifiek item op.
     *
     * @param id
     *            id
     * @param toegangLeveringsautorisatieId
     *            id van toegang
     * @return item
     * @throws NotFoundException
     *             wanneer het item niet gevonden kan worden
     */
    @RequestMapping(value = "/{id}/toegangen/{tid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final ToegangLeveringsautorisatie getToegangLeveringsautorisatie(
        @PathVariable("id") final Integer id,
        @PathVariable("tid") final Integer toegangLeveringsautorisatieId) throws ErrorHandler.NotFoundException
    {
        return toegangLeveringsautorisatieController.get(toegangLeveringsautorisatieId);
    }

    /**
     * Haal een lijst van items op.
     *
     * @param id
     *            id van Leveringsautorisatie
     * @param parameters
     *            request parameters
     * @param pageable
     *            paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/{id}/toegangen", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final Page<ToegangLeveringsautorisatie> listToegangLeveringsautorisatie(
        @PathVariable(value = "id") final String id,
        @RequestParam final Map<String, String> parameters,
        @PageableDefault(size = 10) final Pageable pageable)
    {
        parameters.put(ToegangLeveringsautorisatieController.PARAMETER_FILTER_LEVERINGSAUTORISATIE, id);
        return toegangLeveringsautorisatieController.list(parameters, pageable);
    }

    /**
     * Slaat een gewijzigd item op.
     *
     * @param item
     *            item
     * @return item
     * @throws NotFoundException
     *             wanneer het item niet gevonden kan worden om te updaten
     */
    @RequestMapping(value = "/{id}/toegangen", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final ToegangLeveringsautorisatie saveToegangAbonnement(@RequestBody final ToegangLeveringsautorisatie item) throws NotFoundException {
        return toegangLeveringsautorisatieController.save(item);
    }

    // /**
    // * Haal een lijst van items op.
    // *
    // * @param id id van toegangabonnement
    // * @param parameters request parameters
    // * @param pageable paginering
    // * @return lijst van item (inclusief paginering en sortering)
    // */
    // @RequestMapping(value = "/{id}/lo3rubrieken", method = RequestMethod.GET)
    // @ResponseStatus(HttpStatus.OK)
    // public final Page<DienstbundelLO3Rubriek> listLo3FilterRubriek(
    // @PathVariable(value = "id") final String id,
    // @RequestParam final Map<String, String> parameters,
    // @PageableDefault(size = 2000) final Pageable pageable)
    // {
    // parameters.put(DienstbundelLO3RubriekController.PARAMETER_FILTER_ABONNEMENT, id);
    // return lo3FilterRubriekController.list(parameters, pageable);
    // }
    //
    // /**
    // * Slaat een gewijzigd item op.
    // *
    // * @param item item
    // * @return item
    // * @throws NotFoundException wanneer het item niet gevonden kan worden om te updaten
    // */
    // @RequestMapping(value = "/{id}/lo3rubrieken", method = RequestMethod.POST)
    // @ResponseStatus(HttpStatus.OK)
    // public final DienstbundelLO3Rubriek saveLo3FilterRubriek(@RequestBody final DienstbundelLO3Rubriek item) throws
    // NotFoundException {
    // return lo3FilterRubriekController.save(item);
    // }

}
