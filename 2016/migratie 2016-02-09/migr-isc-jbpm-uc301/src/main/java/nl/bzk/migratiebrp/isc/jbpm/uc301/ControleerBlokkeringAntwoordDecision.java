/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer blokkering antwoord.
 */
@Component("uc301ControleerBlokkeringAntwoordDecision")
public final class ControleerBlokkeringAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BlokkeringAntwoordBericht blokkeringAntwoord =
                (BlokkeringAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("blokkeringAntwoordBericht"));

        final String transitionName;

        if (StatusType.OK.equals(blokkeringAntwoord.getStatus())) {
            transitionName = null;
        } else if (StatusType.GEBLOKKEERD.equals(blokkeringAntwoord.getStatus())) {
            transitionName = "6c. Geblokkeerd";
        } else {
            transitionName = "6b. Fout";
        }

        return transitionName;
    }
}
