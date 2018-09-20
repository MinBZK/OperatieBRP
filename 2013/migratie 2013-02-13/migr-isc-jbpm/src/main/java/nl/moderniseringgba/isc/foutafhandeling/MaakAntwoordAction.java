/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.BrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;

import org.springframework.stereotype.Component;

/**
 * Maak het foutnotificatie bericht.
 */
@Component("foutafhandelingMaakAntwoordAction")
public final class MaakAntwoordAction implements SpringAction {

    private static final String DEFAULT_TOELICHTING = "Het proces is fout gegaan.";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final BrpBericht bericht = (BrpBericht) parameters.get(FoutafhandelingConstants.BERICHT_BRP);
        if (bericht == null) {
            throw new IllegalStateException("Geen BRP bericht om met notificatie te beantwoorden.");
        }

        if (!(bericht instanceof BrpVerzoekBericht)) {
            throw new IllegalStateException(
                    "BRP bericht is niet een BRP Verzoek bericht waarop een antwoord gegeven moet worden.");
        }

        final BrpAntwoordBericht antwoord = ((BrpVerzoekBericht) bericht).maakAntwoordBericht();
        antwoord.setCorrelationId(bericht.getMessageId());
        antwoord.setStatus(StatusType.FOUT);

        final String foutmeldingText = (String) parameters.get(FoutafhandelingConstants.FOUTMELDING);
        if (foutmeldingText != null && !"".equals(foutmeldingText)) {
            antwoord.setToelichting(foutmeldingText);
        } else {
            antwoord.setToelichting(DEFAULT_TOELICHTING);
        }

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(FoutafhandelingConstants.BERICHT_BRP_ANTWOORD, antwoord);

        return result;
    }
}
