/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer zoek persoon antwoord.
 */
@Component("uc301ControleerZoekPersoonBinnenGemeenteAntwoordDecision")
public final class ControleerZoekPersoonBinnenGemeenteAntwoordDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ZoekPersoonAntwoordBericht zoekAntwoord =
                parameters == null ? null : (ZoekPersoonAntwoordBericht) parameters
                        .get("zoekPersoonBinnenGemeenteAntwoordBericht");

        LOG.info("zoekPersoonBinnenGemeenteAntwoordBericht: {}", zoekAntwoord);

        final String result;
        if (zoekAntwoord == null || zoekAntwoord.getStatus() != StatusType.OK) {
            result = "4b. Fout";
        } else if (zoekAntwoord.getAantalGevondenPersonen() == 0) {
            result = "4d. Geen persoon";
        } else if (zoekAntwoord.getAantalGevondenPersonen() > 1) {
            result = "4c. Meerdere personen";
        } else {
            // Ok
            return null;
        }

        return result;

    }
}
