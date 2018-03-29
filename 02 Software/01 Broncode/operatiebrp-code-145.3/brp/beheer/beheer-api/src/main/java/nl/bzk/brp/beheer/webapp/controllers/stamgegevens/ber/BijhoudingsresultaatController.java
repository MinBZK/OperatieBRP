/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.ber;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Bijhoudingsresultaat controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.BIJHOUDINGSRESULTAAT_URI)
public final class BijhoudingsresultaatController extends AbstractReadonlyController<BijhoudingResultaat, Integer> {

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected BijhoudingsresultaatController(@Named("bijhoudingResultaatRepository") final ReadonlyRepository<BijhoudingResultaat, Integer> repository) {
        super(repository);
    }

}
