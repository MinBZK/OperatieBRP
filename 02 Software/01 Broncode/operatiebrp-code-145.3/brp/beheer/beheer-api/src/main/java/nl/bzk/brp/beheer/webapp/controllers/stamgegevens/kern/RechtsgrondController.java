/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rechtsgrond controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.RECHTSGROND_URI)
public final class RechtsgrondController extends AbstractReadWriteController<Rechtsgrond, Short> {

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected RechtsgrondController(final ReadWriteRepository<Rechtsgrond, Short> repository) {
        super(repository);
    }

}
