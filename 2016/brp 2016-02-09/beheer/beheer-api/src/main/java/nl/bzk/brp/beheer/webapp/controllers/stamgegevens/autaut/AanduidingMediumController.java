/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.AanduidingMedium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AanduidingMedium controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.AANDUIDING_MEDIUM_URI)
public final class AanduidingMediumController extends AbstractReadonlyController<AanduidingMedium, Integer> {

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected AanduidingMediumController(@Named("aanduidingMediumRepository") final ReadonlyRepository<AanduidingMedium, Integer> repository) {
        super(repository);
    }

}
