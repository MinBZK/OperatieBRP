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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SoortActie controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.SOORT_ACTIE_BRON_GEBRUIK_URI)
public final class SoortActieBrongebruikController extends AbstractReadWriteController<SoortActieBrongebruik, Short> {

    private static final String SOORT_ACTIE = "soortActieBrongebruikSleutel.soortActieId";
    private static final String SOORT_ADMINISTRATIEVE_HANDELING = "soortActieBrongebruikSleutel.soortAdministratieveHandelingId";
    private static final String SOORT_DOCUMENT = "soortActieBrongebruikSleutel.soortDocument.naam";
    private static final String SOORT_DOCUMENT_ID = "soortActieBrongebruikSleutel.soortDocument.id";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected SoortActieBrongebruikController(
        @Named("soortActieBrongebruikRepository") final ReadWriteRepository<SoortActieBrongebruik, Short> repository) {
        super(repository,
                Arrays.asList(
                        new Filter<>(SOORT_ACTIE, new ShortValueAdapter(), new EqualPredicateBuilderFactory(SOORT_ACTIE)),
                        new Filter<>(
                                SOORT_ADMINISTRATIEVE_HANDELING,
                                new ShortValueAdapter(),
                                new EqualPredicateBuilderFactory(SOORT_ADMINISTRATIEVE_HANDELING)),
                        new Filter<>(SOORT_DOCUMENT_ID, new ShortValueAdapter(), new EqualPredicateBuilderFactory(SOORT_DOCUMENT_ID))),
                Collections.emptyList(),
                Arrays.asList(SOORT_DOCUMENT, SOORT_ACTIE, SOORT_ADMINISTRATIEVE_HANDELING));
    }

}
