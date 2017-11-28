/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselConversie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConversieVoorvoegsel controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_VOORVOEGSEL_URI)
public final class ConversieVoorvoegselController extends AbstractReadWriteController<VoorvoegselConversie, Integer> {

    private static final String SCHEIDINGSTEKEN = "scheidingsteken";
    private static final String VOORVOEGSEL = "voorvoegsel";
    private static final String LO3_VOORVOEGSEL = "lo3Voorvoegsel";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ConversieVoorvoegselController(
        @Named("conversieVoorvoegselRepository") final ReadWriteRepository<VoorvoegselConversie, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(LO3_VOORVOEGSEL, new StringValueAdapter(), new LikePredicateBuilderFactory(LO3_VOORVOEGSEL)),
                        new Filter<>(VOORVOEGSEL, new StringValueAdapter(), new LikePredicateBuilderFactory(VOORVOEGSEL)),
                        new Filter<>(SCHEIDINGSTEKEN, new StringValueAdapter(), new LikePredicateBuilderFactory(SCHEIDINGSTEKEN))),
                null,
                Arrays.asList(LO3_VOORVOEGSEL, VOORVOEGSEL, SCHEIDINGSTEKEN));
    }

}
