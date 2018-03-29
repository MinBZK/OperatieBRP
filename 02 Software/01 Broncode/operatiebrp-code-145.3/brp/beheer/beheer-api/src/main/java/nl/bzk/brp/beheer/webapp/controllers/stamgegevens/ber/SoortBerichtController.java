/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.ber;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SoortBericht controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.SOORT_BERICHT_URI)
public final class SoortBerichtController extends AbstractReadonlyController<SoortBericht, Integer> {

    private static final String IDENTIFIER = "identifier";
    private static final String STELSEL = "koppelvlak.stelsel.naam";
    private static final String STELSEL_ID = "koppelvlak.stelsel.id";
    private static final String KOPPELVLAK = "koppelvlak.naam";
    private static final String KOPPELVLAK_ID = "koppelvlak.id";
    private static final String MODULE = "module";
    private static final String MODULE_ID = "module.id";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected SoortBerichtController(@Named("soortBerichtRepository") final ReadonlyRepository<SoortBericht, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(IDENTIFIER, new StringValueAdapter(), new LikePredicateBuilderFactory(IDENTIFIER)),
                        new Filter<>(STELSEL_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(STELSEL_ID)),
                        new Filter<>(KOPPELVLAK_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(KOPPELVLAK_ID)),
                        new Filter<>(MODULE_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(MODULE_ID))),
                Arrays.asList(IDENTIFIER, STELSEL, KOPPELVLAK, MODULE));
    }

}
