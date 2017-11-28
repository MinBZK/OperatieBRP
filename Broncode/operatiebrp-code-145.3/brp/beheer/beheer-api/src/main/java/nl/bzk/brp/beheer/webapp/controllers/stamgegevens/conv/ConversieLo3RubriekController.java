/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Conversie LO3 Rubriek controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_LO3_RUBRIEK_URI)
public class ConversieLo3RubriekController extends AbstractReadWriteController<Lo3Rubriek, Integer> {

    private static final String NAAM = "naam";
    private static final List<String> SORTERINGEN = Arrays.asList(NAAM);

    private static final Filter<?> FILTER_NAAM = new Filter<>(NAAM, new StringValueAdapter(), new EqualPredicateBuilderFactory(NAAM));

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ConversieLo3RubriekController(@Named("lo3RubriekRepository") final ReadWriteRepository<Lo3Rubriek, Integer> repository) {
        super(repository, Arrays.<Filter<?>>asList(FILTER_NAAM), Collections.<HistorieVerwerker<Lo3Rubriek>>emptyList(), SORTERINGEN);
    }
}
