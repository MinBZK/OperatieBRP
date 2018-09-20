/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.DateValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EnumValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.GreaterOrEqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.LessOrEqualPredicateBuilderFactory;
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
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Berichten controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.BERICHT_URI)
public final class BerichtController extends AbstractBaseReadonlyController<BerichtView, BerichtModel, Long> {

    private static final String SOORT_SYNCHRONISATIE = "parameters.soortSynchronisatie";
    private static final String VERWERKINGSWIJZE = "parameters.verwerkingswijze";
    private static final String DATUM_ONTVANGST = "stuurgegevens.datumTijdOntvangst";
    private static final String DATUM_VERZENDING = "stuurgegevens.datumTijdVerzending";
    private static final String ONTVANGENDE_PARTIJ = "stuurgegevens.ontvangendePartijId";
    private static final String ZENDENDE_PARTIJ = "stuurgegevens.zendendePartijId";
    private static final String CROSS_REFERENTIE_NUMMER = "stuurgegevens.crossReferentienummer";
    private static final String REFERENTIE_NUMMER = "stuurgegevens.referentienummer";
    private static final String RESULTAAT_BIJHOUDING = "resultaat.bijhouding";
    private static final String SOORT = "soort";

    private static final String ATTRIBUUT_DATUM_TIJD_VERZENDING = DATUM_VERZENDING;
    private static final String ATTRIBUUT_DATUM_TIJD_ONTVANGST = DATUM_ONTVANGST;
    private static final Filter<?> FILTER_ID = new Filter<>("filterId", new LongValueAdapter(), new EqualPredicateBuilderFactory("ID"));
    private static final Filter<?> FILTER_SOORT = new Filter<>("filterSoort", new AttribuutValueAdapter<>(
        new EnumValueAdapter<>(SoortBericht.class),
        SoortBerichtAttribuut.class), new EqualPredicateBuilderFactory(SOORT));
    private static final Filter<?> FILTER_ZENDENDE_PARTIJ = new Filter<>(
        "filterZendendePartij",
        new ShortValueAdapter(),
        new EqualPredicateBuilderFactory(ZENDENDE_PARTIJ));
    private static final Filter<?> FILTER_ONTVANGENDE_PARTIJ = new Filter<>(
        "filterOntvangendePartij",
        new ShortValueAdapter(),
        new EqualPredicateBuilderFactory(ONTVANGENDE_PARTIJ));
    private static final Filter<?> FILTER_REFERENTIENUMMER = new Filter<>("filterReferentienummer", new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        ReferentienummerAttribuut.class), new OrPredicateBuilderFactory<>(
        new EqualPredicateBuilderFactory(REFERENTIE_NUMMER),
        new EqualPredicateBuilderFactory(CROSS_REFERENTIE_NUMMER)));
    private static final Filter<?> FILTER_BEGINDATUM = new Filter<>("filterBegindatum", new AttribuutValueAdapter<>(
        new DateValueAdapter(),
        DatumTijdAttribuut.class), new OrPredicateBuilderFactory<>(new GreaterOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(
        ATTRIBUUT_DATUM_TIJD_VERZENDING), new GreaterOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(ATTRIBUUT_DATUM_TIJD_ONTVANGST)));
    private static final Filter<?> FILTER_EINDDATUM = new Filter<>("filterEinddatum", new AttribuutValueAdapter<>(
        new DateValueAdapter(),
        DatumTijdAttribuut.class), new OrPredicateBuilderFactory<>(new LessOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(
        ATTRIBUUT_DATUM_TIJD_VERZENDING), new LessOrEqualPredicateBuilderFactory<DatumTijdAttribuut>(ATTRIBUUT_DATUM_TIJD_ONTVANGST)));
    private static final Filter<?> FILTER_SOORT_SYNCHRONISATIE = new Filter<>("filterSoortSynchronisatie", new AttribuutValueAdapter<>(
        new EnumValueAdapter<>(SoortSynchronisatie.class),
        SoortSynchronisatieAttribuut.class), new EqualPredicateBuilderFactory(SOORT_SYNCHRONISATIE));
    private static final Filter<?> FILTER_VERWERKINGSWIJZE = new Filter<>("filterVerwerkingswijze", new AttribuutValueAdapter<>(new EnumValueAdapter<>(
        Verwerkingswijze.class), VerwerkingswijzeAttribuut.class), new EqualPredicateBuilderFactory(VERWERKINGSWIJZE));
    private static final Filter<?> FILTER_BIJHOUDING = new Filter<>("filterBijhouding", new AttribuutValueAdapter<>(new EnumValueAdapter<>(
        Bijhoudingsresultaat.class), BijhoudingsresultaatAttribuut.class), new EqualPredicateBuilderFactory(RESULTAAT_BIJHOUDING));
    private static final Filter<?> FILTER_ZENDENDE_SYSTEEM = new Filter<>("filterZendendeSysteem", new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        SysteemNaamAttribuut.class), new EqualPredicateBuilderFactory("stuurgegevens.zendendeSysteem"));
    private static final Filter<?> FILTER_ONTVANGENDE_SYSTEEM = new Filter<>("filterOntvangendeSysteem", new AttribuutValueAdapter<>(
        new StringValueAdapter(),
        SysteemNaamAttribuut.class), new EqualPredicateBuilderFactory("stuurgegevens.ontvangendeSysteem"));
    private static final Filter<?> FILTER_CATEGORIE_DIENST = new Filter<>("filterCategorieDienst", new AttribuutValueAdapter<>(new EnumValueAdapter<>(
        SoortDienst.class), SoortDienstAttribuut.class), new EqualPredicateBuilderFactory("parameters.categorieDienst"));
    private static final Filter<?> FILTER_ADMINISTRATIEVE_HANDELING = new Filter<>(
        "filterAdministratieveHandeling",
        new LongValueAdapter(),
        new EqualPredicateBuilderFactory("standaard.administratieveHandelingId"));
    private static final List<String> SORTERINGEN = Arrays.asList(
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
     *
     * @param repository
     *            bericht repository
     * @param administratieveHandelingRepository
     *            administratieve handeling repository
     * @param leveringsautorisatieRepository
     *            abonnement repository
     */
    @Autowired
    protected BerichtController(
        final BerichtRepository repository,
        final AdministratieveHandelingRepository administratieveHandelingRepository,
        final LeveringsautorisatieRepository leveringsautorisatieRepository)
    {
        super(repository, Arrays.asList(
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
            FILTER_ONTVANGENDE_SYSTEEM,
            FILTER_CATEGORIE_DIENST,
            FILTER_ADMINISTRATIEVE_HANDELING), SORTERINGEN, false);
        this.administratieveHandelingRepository = administratieveHandelingRepository;
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
    }

    @Override
    protected BerichtView converteerNaarView(final BerichtModel item) {
        final AdministratieveHandelingModel administratieveHandeling;
        if (item.getStandaard() != null && item.getStandaard().getAdministratieveHandelingId() != null) {
            administratieveHandeling = administratieveHandelingRepository.findOne(item.getStandaard().getAdministratieveHandelingId());
        } else {
            administratieveHandeling = null;
        }

        final Leveringsautorisatie leveringsautorisatie;
        if (item.getParameters() != null && item.getParameters().getLeveringsautorisatieId() != null) {
            leveringsautorisatie = leveringsautorisatieRepository.findOne(item.getParameters().getLeveringsautorisatieId());
        } else {
            leveringsautorisatie = null;

        }

        return new BerichtDetailView(item, administratieveHandeling, leveringsautorisatie);
    }

    @Override
    protected List<BerichtView> converteerNaarView(final List<BerichtModel> content) {
        if (content == null) {
            return null;
        }

        final List<BerichtView> resultaat = new ArrayList<>();
        for (final BerichtModel item : content) {
            resultaat.add(new BerichtListView(item));
        }

        return resultaat;
    }
}
