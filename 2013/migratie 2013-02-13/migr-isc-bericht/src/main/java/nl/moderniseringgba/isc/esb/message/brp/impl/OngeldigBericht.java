/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.moderniseringgba.isc.esb.message.brp.BrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.w3c.dom.Document;

/**
 * Bericht om aan te geven dat een bericht ongeldig is.
 */
public final class OngeldigBericht extends nl.moderniseringgba.isc.esb.message.impl.OngeldigBericht implements
        BrpVerzoekBericht {

    private static final Pattern DOC_ELEMENT_PATTERN = Pattern.compile("<([a-zA-Z]+:)?([a-zA-Z]+)");

    private static final long serialVersionUID = 1L;

    /**
     * Constructor (gebruikt in ESB).
     * 
     * @param bericht
     *            bericht inhoud
     * @param melding
     *            melding
     */
    public OngeldigBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    @Override
    public void parse(final Document document) {
    }

    @Override
    public BrpAntwoordBericht maakAntwoordBericht() {
        final String documentElement = getDocumentElement(getBericht());
        final BrpAntwoordBericht antwoord = getBrpAntwoordBericht(documentElement);
        antwoord.setCorrelationId(this.getMessageId());
        antwoord.setStatus(StatusType.FOUT);
        antwoord.setToelichting(getMelding());

        return antwoord;
    }

    private BrpAntwoordBericht getBrpAntwoordBericht(final String documentElementVerzoekBericht) {
        final BrpAntwoordBericht result;
        if ("erkenningVerzoek".equalsIgnoreCase(documentElementVerzoekBericht)) {
            result = new ErkenningAntwoordBericht();
        } else if ("erkenningNotarieelVerzoek".equalsIgnoreCase(documentElementVerzoekBericht)) {
            result = new ErkenningNotarieelAntwoordBericht();
        } else if ("geboorteVerzoek".equalsIgnoreCase(documentElementVerzoekBericht)) {
            result = new GeboorteAntwoordBericht();
        } else if ("verhuizingVerzoek".equalsIgnoreCase(documentElementVerzoekBericht)) {
            result = new VerhuizingAntwoordBericht();
        } else {
            result = new ErrorBericht();
        }
        return result;
    }

    private String getDocumentElement(final String bericht) {
        final Matcher matcher = DOC_ELEMENT_PATTERN.matcher(bericht);
        if (matcher.find()) {
            return matcher.group(2);
        } else {
            return null;
        }
    }
}
