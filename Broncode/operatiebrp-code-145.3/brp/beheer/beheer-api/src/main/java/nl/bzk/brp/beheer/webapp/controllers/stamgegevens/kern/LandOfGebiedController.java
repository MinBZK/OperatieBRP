/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LandGebied controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.LAND_GEBIED_URI)
public final class LandOfGebiedController extends AbstractReadWriteController<LandOfGebied, Integer> {

    private static final String ISO31661ALPHA2 = "iso31661Alpha2";
    private static final String CODE = "code";
    private static final String NAAM = "naam";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected LandOfGebiedController(@Named("landGebiedRepository") final ReadWriteRepository<LandOfGebied, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(CODE, new StringValueAdapter(), new EqualPredicateBuilderFactory(CODE)),
                        new Filter<>(NAAM, new StringValueAdapter(), new LikePredicateBuilderFactory(NAAM)),
                        new Filter<>(ISO31661ALPHA2, new StringValueAdapter(), new LikePredicateBuilderFactory(ISO31661ALPHA2))),
                null,
                Arrays.asList(NAAM, CODE, ISO31661ALPHA2));
    }

}
