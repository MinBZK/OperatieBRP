/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.beheer.kern.RedenVerliesNLNationaliteit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RedenVerliesNLNationaliteit controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.REDEN_VERLIES_NL_NATIONALITEIT_URI)
public final class RedenVerliesNLNationaliteitController extends AbstractReadWriteController<RedenVerliesNLNationaliteit, Short> {

    /**
     * Constructor.
     *
     * @param repository repository
     */
    @Autowired
    protected RedenVerliesNLNationaliteitController(
        @Named("redenVerliesNLNationaliteitRepository") final ReadWriteRepository<RedenVerliesNLNationaliteit, Short> repository)
    {
        super(repository);
    }

}
