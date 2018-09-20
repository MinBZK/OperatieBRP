/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EnumValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.JaNeeAttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.OrPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ReferenceValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.kern.AdellijkeTitelReadOnlyRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.AdministratieveHandelingHisVolledigImplRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonHisVolledigRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.PredicaatReadOnlyRepository;
import nl.bzk.brp.beheer.webapp.view.PersoonDetailView;
import nl.bzk.brp.beheer.webapp.view.PersoonListView;
import nl.bzk.brp.beheer.webapp.view.PersoonView;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Persoon controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.PERSOON_URI)
public final class PersoonController extends AbstractBaseReadonlyController<PersoonView, PersoonModel, Integer> {

    private static final String ID = "ID";
    private static final String SOORT = "soort";
    private static final String GEBOORTEDATUM_GEBOORTE = "geboorte.datumGeboorte";
    private static final String IDENTIFICATIENUMMERS_BURGERSERVICENUMMER = "identificatienummers.burgerservicenummer";
    private static final String IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER = "identificatienummers.administratienummer";
    private static final String SAMENGESTELDE_NAAMGESLACHTSNAAMSTAM = "samengesteldeNaam.geslachtsnaamstam";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Filter<?> FILTER_ID = new Filter<>("iD", new IntegerValueAdapter(), new EqualPredicateBuilderFactory(ID));

    private static final Filter<?> FILTER_BSN = new Filter<>("bsn", new IntegerValueAdapter(), new OrPredicateBuilderFactory(
            new EqualPredicateBuilderFactory(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER),
            new EqualPredicateBuilderFactory("nummerverwijzing.volgendeBurgerservicenummer"),
            new EqualPredicateBuilderFactory("nummerverwijzing.vorigeBurgerservicenummer")));

    private static final Filter<?> FILTER_ANR = new Filter<>("anr", new LongValueAdapter(), new OrPredicateBuilderFactory(
            new EqualPredicateBuilderFactory(IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER),
            new EqualPredicateBuilderFactory("nummerverwijzing.volgendeAdministratienummer"),
            new EqualPredicateBuilderFactory("nummerverwijzing.vorigeAdministratienummer")));
    private static final Filter<?> FILTER_SOORT = new Filter<>(SOORT, new AttribuutValueAdapter<>(
            new EnumValueAdapter<>(SoortPersoon.class),
            SoortPersoonAttribuut.class), new EqualPredicateBuilderFactory(SOORT));
    private static final Filter<?> FILTER_AFGELEID = new Filter<>("afgeleid", new JaNeeAttribuutValueAdapter(),
            new EqualPredicateBuilderFactory("samengesteldeNaam.indicatieAfgeleid"));
    private static final Filter<?> FILTER_NAMENREEKS = new Filter<>("namenreeks", new JaNeeAttribuutValueAdapter(),
            new EqualPredicateBuilderFactory("samengesteldeNaam.indicatieNamenreeks"));
    private static final Filter<?> FILTER_VOORNAMEN = new Filter<>("voornamen", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            VoornamenAttribuut.class),
            new OrPredicateBuilderFactory(
                    new LikePredicateBuilderFactory("samengesteldeNaam.voornamen"),
                    new LikePredicateBuilderFactory("naamgebruik.voornamenNaamgebruik")));
    private static final Filter<?> FILTER_VOORVOEGSEL = new Filter<>("voorvoegsel", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            VoorvoegselAttribuut.class),
            new OrPredicateBuilderFactory(
                    new LikePredicateBuilderFactory("samengesteldeNaam.voorvoegsel"),
                    new LikePredicateBuilderFactory("naamgebruik.voorvoegselNaamgebruik")));
    private static final Filter<?> FILTER_SCHEIDINGSTEKEN = new Filter<>("scheidingsteken", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            ScheidingstekenAttribuut.class),
            new OrPredicateBuilderFactory(
                    new LikePredicateBuilderFactory("samengesteldeNaam.scheidingsteken"),
                    new LikePredicateBuilderFactory("naamgebruik.scheidingstekenNaamgebruik")));
    private static final Filter<?> FILTER_GESLACHTSNAAMSTAM = new Filter<>("geslachtsnaamstam", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            GeslachtsnaamstamAttribuut.class),
            new OrPredicateBuilderFactory(
                    new LikePredicateBuilderFactory(SAMENGESTELDE_NAAMGESLACHTSNAAMSTAM),
                    new LikePredicateBuilderFactory("naamgebruik.geslachtsnaamstamNaamgebruik")));
    private static final Filter<?> FILTER_GEBOORTE_DATUM = new Filter<>("geboortedatum", new AttribuutValueAdapter<>(
            new IntegerValueAdapter(),
            DatumEvtDeelsOnbekendAttribuut.class),
            new EqualPredicateBuilderFactory(GEBOORTEDATUM_GEBOORTE));
    private static final Filter<?> FILTER_GEBOORTE_GEMEENTE = new Filter<>("geboortegemeente", new AttribuutValueAdapter<>(
            new ShortValueAdapter(),
            GemeenteCodeAttribuut.class),
            new EqualPredicateBuilderFactory("geboorte.gemeenteGeboorte.waarde.code"));
    private static final Filter<?> FILTER_GEBOORTE_WOONPLAATS = new Filter<>("geboortewoonplaats", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            NaamEnumeratiewaardeAttribuut.class),
            new LikePredicateBuilderFactory("geboorte.woonplaatsnaamGeboorte"));
    private static final Filter<?> FILTER_GEBOORTE_BUITENLANDSE_PLAATS = new Filter<>("geboortebuitenlandseplaats", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            BuitenlandsePlaatsAttribuut.class),
            new LikePredicateBuilderFactory("geboorte.buitenlandsePlaatsGeboorte"));
    private static final Filter<?> FILTER_GEBOORTE_BUITENLANDSE_REGIO = new Filter<>("geboortebuitenlandseregio", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            BuitenlandseRegioAttribuut.class),
            new LikePredicateBuilderFactory("geboorte.buitenlandseRegioGeboorte"));
    private static final Filter<?> FILTER_GEBOORTE_LAND = new Filter<>("geboorteland", new AttribuutValueAdapter<>(
            new ShortValueAdapter(),
            LandGebiedCodeAttribuut.class),
            new EqualPredicateBuilderFactory("geboorte.landGebiedGeboorte.waarde.code"));

    private static final List<String> SORTERINGEN = Arrays.asList(
            IDENTIFICATIENUMMERS_BURGERSERVICENUMMER,
            IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER,
            SAMENGESTELDE_NAAMGESLACHTSNAAMSTAM,
            ID);

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private AdministratieveHandelingHisVolledigImplRepository administratieveHandelingHisVolledigImplRepository;

    /**
     * Constructor.
     *
     * @param repository persoon repository
     * @param predicaatReadOnlyRepository predicaat repo
     * @param adellijkeTitelReadOnlyRepository adellijke titel repo
     */
    @Autowired
    protected PersoonController(final PersoonRepository repository,
            final PredicaatReadOnlyRepository predicaatReadOnlyRepository,
            final AdellijkeTitelReadOnlyRepository adellijkeTitelReadOnlyRepository)
    {
        super(repository, Arrays.<Filter<?>>asList(
                FILTER_ID,
                FILTER_BSN,
                FILTER_ANR,
                FILTER_SOORT,
                FILTER_AFGELEID,
                FILTER_NAMENREEKS,
                new Filter<>("predicaat", new ReferenceValueAdapter<>(
                                new ShortValueAdapter(), predicaatReadOnlyRepository),
                        new OrPredicateBuilderFactory(
                                new EqualPredicateBuilderFactory("samengesteldeNaam.predicaat.waarde"),
                                new EqualPredicateBuilderFactory("naamgebruik.predicaatNaamgebruik.waarde"))),
                new Filter<>("adellijketitel", new ReferenceValueAdapter<>(
                                new ShortValueAdapter(), adellijkeTitelReadOnlyRepository),
                        new OrPredicateBuilderFactory(
                                new EqualPredicateBuilderFactory("samengesteldeNaam.adellijkeTitel.waarde"),
                                new EqualPredicateBuilderFactory("naamgebruik.adellijkeTitelNaamgebruik.waarde"))),
                FILTER_VOORNAMEN,
                FILTER_VOORVOEGSEL,
                FILTER_SCHEIDINGSTEKEN,
                FILTER_GESLACHTSNAAMSTAM,
                FILTER_GEBOORTE_DATUM,
                FILTER_GEBOORTE_GEMEENTE,
                FILTER_GEBOORTE_WOONPLAATS,
                FILTER_GEBOORTE_BUITENLANDSE_PLAATS,
                FILTER_GEBOORTE_BUITENLANDSE_REGIO,
                FILTER_GEBOORTE_LAND), SORTERINGEN, true);
    }

    @Override
    protected PersoonView converteerNaarView(final PersoonModel item) {
        LOG.info("Converteer naar view enkel");
        final PersoonHisVolledig persHisVolledig = persoonHisVolledigRepository.findOne(item.getID());
        final List<AdministratieveHandelingHisVolledigImpl> administratieveHandelingen =
                administratieveHandelingHisVolledigImplRepository.findByPersoonTechnischId(item.getID());
        ((PersoonHisVolledigImpl) persHisVolledig).vulAanMetAdministratieveHandelingen(administratieveHandelingen);
        return new PersoonDetailView(persHisVolledig);
    }

    @Override
    protected List<PersoonView> converteerNaarView(final List<PersoonModel> content) {
        LOG.info("converteerNaarView lijst");
        final List<PersoonView> result = new ArrayList<>();

        for (final PersoonModel model : content) {
            result.add(new PersoonListView(model));
        }

        return result;
    }
}
