/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleert of de status van het ontvangen antwoord op het ZoekPersoonVerzoekBericht OK is.
 */
@Component("uc307ControleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision")
public final class ControleerZoekPersoonBinnenGemeenteAntwoordBerichtDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ZoekPersoonAntwoordBericht zoekAntwoord = (ZoekPersoonAntwoordBericht) parameters.get("brpBericht");
        LOG.info("zoekPersoonBinnenGemeenteAntwoordBericht: {}", zoekAntwoord);

        // Ok
        String transition = null;

        // Niet ok
        if (zoekAntwoord == null || zoekAntwoord.getStatus() != StatusType.OK) {
            transition = "Fout";
        } else if (zoekAntwoord.getAantalGevondenPersonen() == 0) {
            transition = "Niets gevonden";
        } else if (zoekAntwoord.getAantalGevondenPersonen() > 1) {
            transition = "Niet uniek";
        }

        return transition;
    }
}
