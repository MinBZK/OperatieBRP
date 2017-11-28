/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConversieRedenOpschorting controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_REDEN_OPSCHORTING_URI)
public final class ConversieRedenOpschortingController extends AbstractReadWriteController<RedenOpschorting, Integer> {

    private static final String LO3_OMSCHRIJVING = "lo3OmschrijvingRedenOpschorting";
    private static final String REDEN_OPSCHORTING = "redenOpschortingId";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ConversieRedenOpschortingController(
        @Named("conversieRedenOpschortingRepository") final ReadWriteRepository<RedenOpschorting, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(LO3_OMSCHRIJVING, new StringValueAdapter(), new LikePredicateBuilderFactory(LO3_OMSCHRIJVING)),
                        new Filter<>(REDEN_OPSCHORTING, new ShortValueAdapter(), new EqualPredicateBuilderFactory(REDEN_OPSCHORTING))),
                null,
                Arrays.asList(LO3_OMSCHRIJVING, REDEN_OPSCHORTING));
    }

}
