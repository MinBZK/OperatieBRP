/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;

import org.junit.Assert;
import org.junit.Test;

public class ControleerZoekPersoonBuitenGemeenteAntwoordDecisionTest {

    private final ControleerZoekPersoonBuitenGemeenteAntwoordDecision subject =
            new ControleerZoekPersoonBuitenGemeenteAntwoordDecision();

    @Test
    public void testOk() throws BerichtSyntaxException, BerichtInhoudException {
        //@formatter:off
        final ZoekPersoonAntwoordBericht antwoord = (ZoekPersoonAntwoordBericht) BrpBerichtFactory.SINGLETON.getBericht(
            "<zoekPersoonAntwoord xmlns=\"http://www.moderniseringgba.nl/Migratie/0001\">" 
               + "<status>Ok</status>" 
               + "<gevondenPersonen>" 
                   + "<gevondenPersoon>" 
                       + "<aNummer>8172387435</aNummer>" 
                       + "<bijhoudingsgemeente>1900</bijhoudingsgemeente>" 
                   + "</gevondenPersoon>" 
               + "</gevondenPersonen>" 
           + "</zoekPersoonAntwoord>");
        //@formatter:on

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", antwoord);

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testNok() throws BerichtSyntaxException, BerichtInhoudException {
        //@formatter:off
        final ZoekPersoonAntwoordBericht antwoord = (ZoekPersoonAntwoordBericht) BrpBerichtFactory.SINGLETON.getBericht(
            "<zoekPersoonAntwoord xmlns=\"http://www.moderniseringgba.nl/Migratie/0001\">" 
               + "<status>Fout</status>" 
           + "</zoekPersoonAntwoord>");
        //@formatter:on

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", antwoord);

        Assert.assertEquals("22c. Fout", subject.execute(parameters));
    }

}
