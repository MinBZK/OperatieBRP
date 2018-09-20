/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer verhuizing.
 */
@Component("uc811ControleerVerhuizingDecision")
public final class ControleerVerhuizingDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String OK = null;
    private static final String GEEN_VERHUIZING = "13e. Geen verhuizing";

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long blokkeringInfoAntwoordId = (Long) parameters.get("blokkeringInfoAntwoordBericht");
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord =
                (BlokkeringInfoAntwoordBericht) berichtenDao.leesBericht(blokkeringInfoAntwoordId);

        if (blokkeringInfoAntwoord.getPersoonsaanduiding() == null) {
            LOG.debug("Geen verhuizing");
            return GEEN_VERHUIZING;
        } else {
            // Als persoonsaanduiding gevuld is dan moet dit over een verhuizing van BRP naar LO3 (gba/rni) gaan.
            // Anders was het proces al eerder geknald.
            LOG.debug("Verhuizing");
            return OK;
        }
    }
}
