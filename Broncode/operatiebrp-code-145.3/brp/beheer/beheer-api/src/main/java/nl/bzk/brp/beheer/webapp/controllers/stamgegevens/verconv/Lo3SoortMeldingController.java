/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.verconv;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3SoortMelding;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LO3SoortMelding controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.LO3_SOORT_MELDING_URI)
public final class Lo3SoortMeldingController extends AbstractReadonlyController<Lo3SoortMelding, Integer> {

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected Lo3SoortMeldingController(@Named("lo3SoortMeldingRepository") final ReadonlyRepository<Lo3SoortMelding, Integer> repository) {
        super(repository);
    }

}
