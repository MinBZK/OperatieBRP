/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonRepository;
import nl.bzk.brp.beheer.webapp.view.PersoonView;
import nl.bzk.brp.beheer.webapp.view.PersoonViewFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Persoon controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.PERSOON_URI)
public final class PersoonController extends AbstractBaseReadonlyController<PersoonView, Persoon, Long> {

    private static final String ID = "id";
    private static final String IDENTIFICATIENUMMERS_BURGERSERVICENUMMER = "burgerservicenummer";
    private static final String IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER = "administratienummer";
    private static final String SAMENGESTELDE_NAAMGESLACHTSNAAMSTAM = "geslachtsnaamstam";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final List<String> SORTERINGEN =
            Arrays.asList(IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, SAMENGESTELDE_NAAMGESLACHTSNAAMSTAM, ID);

    private final PersoonViewFactory persoonViewFactory;

    /**
     * Constructor.
     * @param repository persoon repository
     * @param persoonViewFactory repository voor persoon view
     */
    @Inject
    protected PersoonController(final PersoonRepository repository, final PersoonViewFactory persoonViewFactory) {
        super(repository,
                Arrays.asList(
                        PersoonFilterFactory.getFilterId(),
                        PersoonFilterFactory.getFilterBsn(),
                        PersoonFilterFactory.getFilterAnr(),
                        PersoonFilterFactory.getFilterSoort(),
                        PersoonFilterFactory.getFilterAfgeleid(),
                        PersoonFilterFactory.getFilterNamenReeks(),
                        PersoonFilterFactory.getFilterPredicaat(),
                        PersoonFilterFactory.getFilterAdellijkeTitel(),
                        PersoonFilterFactory.getFilterVoornamen(),
                        PersoonFilterFactory.getFilterVoorvoegsel(),
                        PersoonFilterFactory.getFilterScheidingsteken(),
                        PersoonFilterFactory.getFilterGeslachtsnaamstam(),
                        PersoonFilterFactory.getFilterGeboorteDatum(),
                        PersoonFilterFactory.getFilterGeboorteGemeente(),
                        PersoonFilterFactory.getFilterGeboorteWoonplaats(),
                        PersoonFilterFactory.getFilterGeboorteBuitenlandsePlaats(),
                        PersoonFilterFactory.getFilterGeboorteBuitenlandseRegio(),
                        PersoonFilterFactory.getFilterGeboorteLand(),
                        PersoonFilterFactory.getFilterSoortAdres(),
                        PersoonFilterFactory.getFilterIdentificatieAdresseerbaarObject(),
                        PersoonFilterFactory.getFilterIdentificatiecodeNummeraanduiding(),
                        PersoonFilterFactory.getFilterGemeente(),
                        PersoonFilterFactory.getFilterNaamOpenbareRuimte(),
                        PersoonFilterFactory.getFilterAfgekorteNaamOpenbareRuimte(),
                        PersoonFilterFactory.getFilterHuisnummer(),
                        PersoonFilterFactory.getFilterHuisletter(),
                        PersoonFilterFactory.getFilterHuisnummertoevoeging(),
                        PersoonFilterFactory.getFilterPostcode(),
                        PersoonFilterFactory.getFilterWoonplaatsnaam(),
                        PersoonFilterFactory.getFilterBuitenlandsAdres(),
                        PersoonFilterFactory.getFilterLandGebied()),
                SORTERINGEN,
                true);
        this.persoonViewFactory = persoonViewFactory;
    }

    @Override
    protected PersoonView converteerNaarView(final Persoon item) {
        LOG.info("Converteer naar view enkel: " + item);
        return persoonViewFactory.mapPersoonDetailView(item);
    }

    @Override
    protected List<PersoonView> converteerNaarView(final List<Persoon> content) {
        LOG.info("converteerNaarView lijst (size=" + content.size() + ")");
        final List<PersoonView> result = new ArrayList<>();

        for (final Persoon model : content) {
            LOG.info("converteerNaarView lijst: " + model);
            result.add(persoonViewFactory.mapPersoonListView(model));
        }

        return result;
    }
}
