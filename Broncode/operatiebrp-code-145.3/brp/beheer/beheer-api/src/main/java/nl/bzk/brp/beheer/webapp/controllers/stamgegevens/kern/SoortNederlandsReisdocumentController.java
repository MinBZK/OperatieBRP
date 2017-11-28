/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
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
 * SoortNederlandsReisdocument controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.SOORT_NEDERLANDS_REISDOCUMENT_URI)
public final class SoortNederlandsReisdocumentController extends AbstractReadWriteController<SoortNederlandsReisdocument, Short> {

    private static final String CODE = "code";
    private static final String OMSCHRIJVING = "omschrijving";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected SoortNederlandsReisdocumentController(
        @Named("soortNederlandsReisdocumentRepository") final ReadWriteRepository<SoortNederlandsReisdocument, Short> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(CODE, new ShortValueAdapter(), new EqualPredicateBuilderFactory(CODE)),
                        new Filter<>(OMSCHRIJVING, new StringValueAdapter(), new LikePredicateBuilderFactory(OMSCHRIJVING))),
                Collections.emptyList(),
                Arrays.asList(CODE, OMSCHRIJVING));
    }

}
