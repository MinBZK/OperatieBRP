/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak verhuizingantwoord: ok.
 */
@Component("uc302MaakVerhuizingWarningAntwoordAction")
public final class MaakVerhuizingWarningAntwoordAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final VerhuizingVerzoekBericht input = (VerhuizingVerzoekBericht) parameters.get("input");

        final VerhuizingAntwoordBericht verhuizingAntwoord = input.maakAntwoordBericht();
        verhuizingAntwoord.setStatus(StatusType.WAARSCHUWING);
        verhuizingAntwoord.setToelichting("Verhuizing gelukt. Blokkering kon niet worden opgeheven.");

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("verhuizingAntwoord", verhuizingAntwoord);
        return result;
    }
}
