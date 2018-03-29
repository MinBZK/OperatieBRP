/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandelingHistorie;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BijhoudingsautorisatieController voor beheer.
 */
@RestController
@RequestMapping(value = ControllerConstants.BIJHOUDINGSAUTORISATIE_SOORT_ADMINISTRATIEVE_HANDELING_URI)
public class BijhoudingsautorisatieSoortAdministratieveHandelingController
        extends AbstractReadWriteController<BijhoudingsautorisatieSoortAdministratieveHandeling, Integer> {

    /**
     * Constructor voor BijhoudingsautorisatieController.
     * @param repository repo
     */
    @Inject
    public BijhoudingsautorisatieSoortAdministratieveHandelingController(final BijhoudingsautorisatieSoortAdministratieveHandelingRepository repository) {
        super(repository,
                Collections.emptyList(),
                Collections.singletonList(new BijhoudingsautorisatieSoortAdministratieveHandelingHistorieVerwerker()),
                Collections.emptyList());
    }

    /**
     * Bijhoudingsautorisatie historie verwerker.
     */
    static class BijhoudingsautorisatieSoortAdministratieveHandelingHistorieVerwerker extends
            AbstractHistorieVerwerker<BijhoudingsautorisatieSoortAdministratieveHandeling, BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> {

        @Override
        public Class<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> historieClass() {
            return BijhoudingsautorisatieSoortAdministratieveHandelingHistorie.class;
        }

        @Override
        public Class<BijhoudingsautorisatieSoortAdministratieveHandeling> entiteitClass() {
            return BijhoudingsautorisatieSoortAdministratieveHandeling.class;
        }

        @Override
        public boolean isLeeg(final BijhoudingsautorisatieSoortAdministratieveHandeling inputItem) {
            // Aangezien een BijhoudingsautorisatieSoortAdministratieveHandelingHistorie geen inhoud heeft wordt hier altijd false geretourneerd zodat
            // de historie verwerker standaard altijd historie aanmaakt.
            return false;
        }
    }
}
