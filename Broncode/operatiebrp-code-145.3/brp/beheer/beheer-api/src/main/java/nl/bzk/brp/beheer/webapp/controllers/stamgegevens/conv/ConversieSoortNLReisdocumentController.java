/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.conv;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
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
 * ConversieSoortNLReisdocument controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.CONVERSIE_SOORT_NL_REISDOCUMENT_URI)
public final class ConversieSoortNLReisdocumentController extends AbstractReadWriteController<SoortNlReisdocument, Integer> {

    private static final String LO3_OMSCHRIJVING = "lo3NederlandsReisdocument";
    private static final String SOORT_NL_REISDOCUMENT = "soortNederlandsReisdocument.omschrijving";
    private static final String SOORT_NL_REISDOCUMENT_ID = "soortNederlandsReisdocument.id";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ConversieSoortNLReisdocumentController(
        @Named("conversieSoortNLReisdocumentRepository") final ReadWriteRepository<SoortNlReisdocument, Integer> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(LO3_OMSCHRIJVING, new StringValueAdapter(), new LikePredicateBuilderFactory(LO3_OMSCHRIJVING)),
                        new Filter<>(SOORT_NL_REISDOCUMENT, new StringValueAdapter(), new EqualPredicateBuilderFactory(SOORT_NL_REISDOCUMENT)),
                        new Filter<>(SOORT_NL_REISDOCUMENT_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(SOORT_NL_REISDOCUMENT_ID))),
                null,
                Arrays.asList(LO3_OMSCHRIJVING, SOORT_NL_REISDOCUMENT));
    }

}
