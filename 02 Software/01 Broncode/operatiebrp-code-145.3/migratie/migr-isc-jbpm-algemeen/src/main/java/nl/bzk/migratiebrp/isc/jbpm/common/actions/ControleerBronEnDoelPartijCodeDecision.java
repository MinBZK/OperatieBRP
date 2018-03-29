/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import org.springframework.stereotype.Component;

/**
 * Controleer bron en doel gemeente voor een BZM bericht dat een cyclus start.
 */
@Component("algControleerBronEnDoelPartijDecision")
public final class ControleerBronEnDoelPartijCodeDecision implements SpringDecision {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "Fout";

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Partij bronPartij = (Partij) parameters.get("bron");
        final Partij doelPartij = (Partij) parameters.get("doel");

        if (bronPartij == null || bronPartij.getStelsel() != Stelsel.BRP || doelPartij == null || doelPartij.getStelsel() != Stelsel.GBA) {
            LOG.debug("Bron en doel gemeente niet correct.");
            return FOUT;
        } else {
            LOG.debug("Bron BRP en doel GBA. Ok");
            return null;
        }
    }

}
