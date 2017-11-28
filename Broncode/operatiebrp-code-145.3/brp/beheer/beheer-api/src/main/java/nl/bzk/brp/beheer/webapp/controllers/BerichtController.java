/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.DateValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.OrPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ber.BerichtRepository;
import nl.bzk.brp.beheer.webapp.repository.kern.AdministratieveHandelingRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.LeveringsautorisatieRepository;
import nl.bzk.brp.beheer.webapp.view.BerichtDetailView;
import nl.bzk.brp.beheer.webapp.view.BerichtListView;
import nl.bzk.brp.beheer.webapp.view.BerichtView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Berichten controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.BERICHT_URI)
public final class BerichtController extends AbstractBaseReadonlyController<BerichtView, Bericht, Long> {

    private static final String SOORT_SYNCHRONISATIE = "soortSynchronisatieId";
    private static final String VERWERKINGSWIJZE = "verwerkingswijzeId";
    private static final String DATUM_ONTVANGST = "datumTijdOntvangst";
    private static final String DATUM_VERZENDING = "datumTijdVerzending";
    private static final String ONTVANGENDE_PARTIJ = "ontvangendePartij";
    private static final String ZENDENDE_PARTIJ = "zendendePartij";
    private static final String CROSS_REFERENTIE_NUMMER = "crossReferentieNummer";
    private static final String REFERENTIE_NUMMER = "referentieNummer";
    private static final String RESULTAAT_BIJHOUDING = "bijhoudingResultaatId";
    private static final String SOORT = "soortBerichtId";

    private static final String ATTRIBUUT_DATUM_TIJD_VERZENDING = DATUM_VERZENDING;
    private static final String ATTRIBUUT_DATUM_TIJD_ONTVANGST = DATUM_ONTVANGST;
    private static final Filter<?> FILTER_ID = new Filter<>("filterId", new LongValueAdapter(), new EqualPredicateBuilderFactory("id"));
    private static final Filter<?> FILTER_SOORT = new Filter<>("filterSoort", new ShortValueAdapter(), new EqualPredicateBuilderFactory(SOORT));
    private static final Filter<?> FILTER_ZENDENDE_PARTIJ =
            new Filter<>("filterZendendePartij", new ShortValueAdapter(), new EqualPredicateBuilderFactory(ZENDENDE_PARTIJ));
    private static final Filter<?> FILTER_ONTVANGENDE_PARTIJ =
            new Filter<>("filterOntvangendePartij", new ShortValueAdapter(), new EqualPredicateBuilderFactory(ONTVANGENDE_PARTIJ));
    private static final Filter<?> FILTER_REFERENTIENUMMER =
            new Filter<>(
                    "filterReferentienummer",
                    new StringValueAdapter(),
                    new OrPredicateBuilderFactory<>().or(new EqualPredicateBuilderFactory(REFERENTIE_NUMMER))
                            .or(new EqualPredicateBuilderFactory(CROSS_REFERENTIE_NUMMER)));
    private static final Filter<?> FILTER_BEGINDATUM =
            new Filter<>(
                    "filterBegindatum",
                    new DateValueAdapter(),
                    new OrPredicateBuilderFactory<Date>().or(new GreaterOrEqualPredicateBuilderFactory<>(ATTRIBUUT_DATUM_TIJD_VERZENDING))
                            .or(new GreaterOrEqualPredicateBuilderFactory<>(ATTRIBUUT_DATUM_TIJD_ONTVANGST)));
    private static final Filter<?> FILTER_EINDDATUM =
            new Filter<>(
                    "filterEinddatum",
                    new DateValueAdapter(),
                    new OrPredicateBuilderFactory<Date>().or(new LessOrEqualPredicateBuilderFactory<>(ATTRIBUUT_DATUM_TIJD_VERZENDING))
                            .or(new LessOrEqualPredicateBuilderFactory<>(ATTRIBUUT_DATUM_TIJD_ONTVANGST)));
    private static final Filter<?> FILTER_SOORT_SYNCHRONISATIE =
            new Filter<>("filterSoortSynchronisatie", new ShortValueAdapter(), new EqualPredicateBuilderFactory(SOORT_SYNCHRONISATIE));
    private static final Filter<?> FILTER_VERWERKINGSWIJZE =
            new Filter<>("filterVerwerkingswijze", new ShortValueAdapter(), new EqualPredicateBuilderFactory(VERWERKINGSWIJZE));
    private static final Filter<?> FILTER_BIJHOUDING =
            new Filter<>("filterBijhouding", new ShortValueAdapter(), new EqualPredicateBuilderFactory(RESULTAAT_BIJHOUDING));
    private static final Filter<?> FILTER_ZENDENDE_SYSTEEM =
            new Filter<>("filterZendendeSysteem", new StringValueAdapter(), new EqualPredicateBuilderFactory("zendendeSysteem"));
    private static final Filter<?> FILTER_ADMINISTRATIEVE_HANDELING =
            new Filter<>("filterAdministratieveHandeling", new LongValueAdapter(), new EqualPredicateBuilderFactory("administratieveHandelingId"));
    private static final Filter<?> FILTER_LEVERINGSAUTORISATIE =
            new Filter<>("filterLeveringsautorisatieNaam", new StringValueAdapter(), new LikePredicateBuilderFactory("leveringsAutorisatie"));
    private static final Filter<?> FILTER_SOORT_DIENST =
            new Filter<>("filterSoortDienst", new ShortValueAdapter(), new EqualPredicateBuilderFactory("dienst.soortDienstId"));
    private static final List<String> SORTERINGEN =
            Arrays.asList(
                    SOORT,
                    ZENDENDE_PARTIJ,
                    ONTVANGENDE_PARTIJ,
                    REFERENTIE_NUMMER,
                    CROSS_REFERENTIE_NUMMER,
                    DATUM_VERZENDING,
                    DATUM_ONTVANGST,
                    SOORT_SYNCHRONISATIE,
                    VERWERKINGSWIJZE,
                    RESULTAAT_BIJHOUDING);

    private final AdministratieveHandelingRepository administratieveHandelingRepository;
    private final LeveringsautorisatieRepository leveringsautorisatieRepository;

    /**
     * Constructor.
     * @param repository bericht repository
     * @param administratieveHandelingRepository administratieve handeling repository
     * @param leveringsautorisatieRepository abonnement repository
     */
    @Inject
    protected BerichtController(
            final BerichtRepository repository,
            final AdministratieveHandelingRepository administratieveHandelingRepository,
            final LeveringsautorisatieRepository leveringsautorisatieRepository) {
        super(repository,
                Arrays.asList(
                        FILTER_ID,
                        FILTER_SOORT,
                        FILTER_ZENDENDE_PARTIJ,
                        FILTER_ONTVANGENDE_PARTIJ,
                        FILTER_REFERENTIENUMMER,
                        FILTER_BEGINDATUM,
                        FILTER_EINDDATUM,
                        FILTER_SOORT_SYNCHRONISATIE,
                        FILTER_VERWERKINGSWIJZE,
                        FILTER_BIJHOUDING,
                        FILTER_ZENDENDE_SYSTEEM,
                        FILTER_ADMINISTRATIEVE_HANDELING,
                        FILTER_LEVERINGSAUTORISATIE,
                        FILTER_SOORT_DIENST),
                SORTERINGEN,
                false);
        this.administratieveHandelingRepository = administratieveHandelingRepository;
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
    }

    @Override
    protected BerichtView converteerNaarView(final Bericht item) {
        final AdministratieveHandeling administratieveHandeling;
        if (item.getAdministratieveHandeling() != null) {
            administratieveHandeling = administratieveHandelingRepository.findOne(item.getAdministratieveHandeling());
        } else {
            administratieveHandeling = null;
        }

        final Leveringsautorisatie leveringsautorisatie;
        if (item.getLeveringsAutorisatie() != null) {
            leveringsautorisatie = leveringsautorisatieRepository.findOne(item.getLeveringsAutorisatie());
        } else {
            leveringsautorisatie = null;

        }

        return new BerichtDetailView(item, administratieveHandeling, leveringsautorisatie);
    }

    @Override
    protected List<BerichtView> converteerNaarView(final List<Bericht> content) {
        if (content == null) {
            return new ArrayList<>();
        }

        final List<BerichtView> resultaat = new ArrayList<>();
        for (final Bericht item : content) {
            resultaat.add(new BerichtListView(item));
        }

        return resultaat;
    }
}
