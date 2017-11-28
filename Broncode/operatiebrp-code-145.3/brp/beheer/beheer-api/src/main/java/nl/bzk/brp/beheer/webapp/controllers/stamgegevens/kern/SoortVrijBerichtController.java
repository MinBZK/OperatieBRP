/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SoortVrijBericht controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.SOORT_VRIJ_BERICHT_URI)
public final class SoortVrijBerichtController extends AbstractReadWriteController<SoortVrijBericht, Short> {

    private static final String NAAM = "naam";

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected SoortVrijBerichtController(@Named("soortVrijBerichtRepository") final ReadWriteRepository<SoortVrijBericht, Short> repository) {
        super(repository,
                Collections.singletonList(new Filter<>(NAAM, new StringValueAdapter(), new LikePredicateBuilderFactory(NAAM))),
                Collections.emptyList(),
                Collections.singletonList(NAAM));
    }

}
