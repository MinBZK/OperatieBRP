/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf03Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;

import org.springframework.stereotype.Component;

/**
 * Maak het pf01 bericht.
 */
@Component("foutafhandelingMaakPfAction")
public final class MaakPfAction implements SpringAction {

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final Lo3Bericht bericht = (Lo3Bericht) parameters.get(FoutafhandelingConstants.BERICHT_LO3);
        if (bericht == null) {
            throw new IllegalStateException("Geen LO3 bericht om met Pf03 te beantwoorden.");
        }

        final Lo3Bericht pfBericht;
        if (bericht instanceof OnbekendBericht) {
            pfBericht = new Pf01Bericht(bericht.getMessageId());
        } else if (bericht instanceof OngeldigBericht) {
            pfBericht = new Pf02Bericht(bericht.getMessageId());
        } else {
            pfBericht = new Pf03Bericht(bericht.getMessageId());
        }

        pfBericht.setBronGemeente(bericht.getDoelGemeente());
        pfBericht.setDoelGemeente(bericht.getBronGemeente());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(FoutafhandelingConstants.BERICHT_PF, pfBericht);
        result.put(FoutafhandelingConstants.INDICATIE_VB01, pfBericht instanceof Pf03Bericht);

        return result;
    }
}
