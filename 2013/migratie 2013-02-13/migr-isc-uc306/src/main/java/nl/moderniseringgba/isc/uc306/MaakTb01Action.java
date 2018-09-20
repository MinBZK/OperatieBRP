/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak Tb01.
 */
@Component("uc306MaakTb01Action")
public final class MaakTb01Action implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String VARIABELE_TB01_BERICHT = "tb01Bericht";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                (ConverteerNaarLo3AntwoordBericht) parameters.get("converteerNaarLo3Antwoord");

        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setLo3Persoonslijst(converteerNaarLo3Antwoord.getLo3Persoonslijst());

        final GeboorteVerzoekBericht geboorteBericht = (GeboorteVerzoekBericht) parameters.get("input");

        final String adresbepalendeOuder =
                (String) parameters.get(BepaalAdresbepalendeOuderAction.ADRES_BEPALENDE_OUDER);

        final String akteNummer = geboorteBericht.getAktenummer();

        tb01Bericht.setHeader(Lo3HeaderVeld.GEZOCHTE_PERSOON, adresbepalendeOuder);
        if (akteNummer != null) {
            tb01Bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, akteNummer);
        }
        // TODO: wat als er geen aktenummer is...

        final Object herhaling = parameters.get("verzendenTb01Herhaling");
        if (herhaling != null) {
            tb01Bericht.setHeader(Lo3HeaderVeld.HERHALING, String.valueOf(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) parameters.get(VARIABELE_TB01_BERICHT);
            tb01Bericht.setMessageId(orgineel.getMessageId());
        }

        // set originator en recipient
        tb01Bericht.setBronGemeente(geboorteBericht.getBrpGemeente().getFormattedStringCode());

        // TODO: BLAUW-325
        // Als we deze gaan oplossen dan ook in de procesdefinition.xml ergens meenemen zodat
        // de variabele doelGemeente op de juiste waarde wordt gezet (voor eventuele foutafhandeling
        // en registratie)
        // tb01Bericht.setDoelGemeente(geboorteBericht.getLo3Gemeente().getFormattedStringCode());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(VARIABELE_TB01_BERICHT, tb01Bericht);

        return result;
    }
}
