/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een II01 bericht op basis van het binnengekomen BRP verhuisbericht.
 */
@Component("uc302MaakIi01Action")
public final class MaakIi01Action implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String PARAMETER_II01_HERHALING = "ii01Herhaling";
    private static final String PARAMETER_II01_BERICHT = "ii01Bericht";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final VerhuizingVerzoekBericht input = (VerhuizingVerzoekBericht) parameters.get("input");

        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        final Object herhaling = parameters.get(PARAMETER_II01_HERHALING);
        if (herhaling != null) {
            ii01Bericht.setHeader(Lo3HeaderVeld.HERHALING, String.valueOf(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) parameters.get(PARAMETER_II01_BERICHT);
            ii01Bericht.setMessageId(orgineel.getMessageId());
        }
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, input.getANummer());
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, input.getBsn());
        ii01Bericht.setBronGemeente(input.getBrpGemeente().getFormattedStringCode());
        ii01Bericht.setDoelGemeente(input.getLo3Gemeente().getFormattedStringCode());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(PARAMETER_II01_BERICHT, ii01Bericht);

        return result;
    }
}
