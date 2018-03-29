/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.migblok;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LongValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Blokkering controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.BLOKKERING_URI)
public final class BlokkeringController extends AbstractReadonlyController<Blokkering, Integer> {

    private static final String ANUMMER = "aNummer";

    private static final Filter<?> FILTER_A_NUMMER = new Filter<>("filterAnummer", new LongValueAdapter(), new EqualPredicateBuilderFactory(ANUMMER));

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected BlokkeringController(@Named("blokkeringRepository") final ReadonlyRepository<Blokkering, Integer> repository) {
        super(repository, Arrays.asList(FILTER_A_NUMMER), Arrays.asList(ANUMMER));
    }

}
