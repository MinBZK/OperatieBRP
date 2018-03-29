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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.kern.ActieRepository;
import nl.bzk.brp.beheer.webapp.view.ActieView;
import nl.bzk.brp.beheer.webapp.view.ActieViewFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ActieController voor inzage acties.
 *
 **/
@RestController
@RequestMapping(value = ControllerConstants.ACTIE_URI)
public final class ActieController extends AbstractBaseReadonlyController<ActieView, BRPActie, Long> {

    /** Parameter om te filteren op: administratieve handeling. */
    public static final String PARAMETER_ADMINISTRATIEVE_HANDELING = "administratieveHandeling";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String DATUM_ONTLENING = "datumOntlening";
    private static final String ADMINISTRATIEVE_HANDELING = PARAMETER_ADMINISTRATIEVE_HANDELING;
    private static final String SOORT = "soortActieId";

    private static final Filter<?> FILTER_ADMINISTRATIEVE_HANDELING =
            new Filter<>(ADMINISTRATIEVE_HANDELING, new LongValueAdapter(), new EqualPredicateBuilderFactory(ADMINISTRATIEVE_HANDELING));
    private static final Filter<?> FILTER_DATUM_ONTLENING =
            new Filter<>(DATUM_ONTLENING, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(DATUM_ONTLENING));

    private static final Filter<?> FILTER_PARTIJ = new Filter<>("partij", new ShortValueAdapter(), new EqualPredicateBuilderFactory("partij.id"));
    private static final Filter<?> FILTER_SOORT = new Filter<>(SOORT, new ShortValueAdapter(), new EqualPredicateBuilderFactory(SOORT));
    private static final List<String> SORTERINGEN = Arrays.asList(SOORT, "datumTijdRegistratie");

    private final ActieViewFactory actieViewFactory;

    /**
     * Constructor voor ActieController.
     *
     * @param repository
     *            actie repository
     * @param actieViewFactory actie view factory
     */
    @Inject
    public ActieController(final ActieRepository repository, final ActieViewFactory actieViewFactory) {
        super(repository, Arrays.<Filter<?>>asList(FILTER_ADMINISTRATIEVE_HANDELING, FILTER_DATUM_ONTLENING, FILTER_PARTIJ, FILTER_SOORT), SORTERINGEN);
        this.actieViewFactory = actieViewFactory;
    }

    @Override
    protected ActieView converteerNaarView(final BRPActie item) {
        return actieViewFactory.mapActieDetailView(item);
    }

    @Override
    protected List<ActieView> converteerNaarView(final List<BRPActie> content) {
        LOG.info("converteerNaarView lijst (size=" + content.size() + ")");
        final List<ActieView> result = new ArrayList<>();

        for (final BRPActie model : content) {
            LOG.info("converteerNaarView lijst: " + model);
            result.add(actieViewFactory.mapActieListView(model));
        }

        return result;
    }

}
