/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.moderniseringgba.isc.esb.message.brp.BrpAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class OngeldigBerichtTest {

    @Test
    public void testGeboorteAntwoord() {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>"
                        + ""
                        + "<!-- Dit is een voorbeeld van hoe de melding van BRP naar ISC eruit zou zien voor uc306, -->"
                        + "<!-- wanneer de XSD voor dit bericht is gebaseerd op de BRP XSD's.                       -->"
                        + ""
                        + "<geboorteVerzoek"
                        + "   xmlns=\"http://www.moderniseringgba.nl/Migratie/0001\""
                        + "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                        + "   xsi:schemaLocation=\"http://www.moderniseringgba.nl/Migratie/0001 ../../main/resources/xsd/BRP_Berichten.xsd\""
                        + "   xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\">" + "   <iscGemeenten>"
                        + "       <lo3Gemeente>0599</lo3Gemeente>" + "<brpGemeente>0600</brpGemeente>"
                        + "</iscGemeenten>";

        final Pattern pattern = Pattern.compile("<([a-zA-Z]+:)?([a-zA-Z]+)");
        final Matcher matcher = pattern.matcher(xml);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals("geboorteVerzoek", matcher.group(2));

        final OngeldigBericht ongeldigBericht = new OngeldigBericht(xml, "toppiewoppie");
        final BrpAntwoordBericht antwoord = ongeldigBericht.maakAntwoordBericht();
        Assert.assertEquals(GeboorteAntwoordBericht.class, antwoord.getClass());
    }

}
