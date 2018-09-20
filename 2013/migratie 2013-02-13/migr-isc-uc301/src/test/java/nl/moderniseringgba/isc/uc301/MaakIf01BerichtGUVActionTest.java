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
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

import org.junit.Assert;
import org.junit.Test;

public class MaakIf01BerichtGUVActionTest {

    private final MaakIf01BerichtGUVAction subject = new MaakIf01BerichtGUVAction();

    @Test
    public void testNietGevonden() throws BerichtSyntaxException, BerichtInhoudException {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        //@formatter:off
        final ZoekPersoonAntwoordBericht antwoord = (ZoekPersoonAntwoordBericht) BrpBerichtFactory.SINGLETON.getBericht(
            "<zoekPersoonAntwoord xmlns=\"http://www.moderniseringgba.nl/Migratie/0001\">"
               + "<status>Ok</status>"
               + "<gevondenPersonen>" 
               + "</gevondenPersonen>" 
           + "</zoekPersoonAntwoord>");
        //@formatter:on

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", antwoord);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("G", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("0000", if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
    }

    @Test
    public void testVerhuisd() throws BerichtSyntaxException, BerichtInhoudException {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

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
        parameters.put("input", ii01Bericht);
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", antwoord);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("V", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("8172387435", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("1900", if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
    }

    @Test
    public void testNietUniek() throws BerichtSyntaxException, BerichtInhoudException {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        //@formatter:off
        final ZoekPersoonAntwoordBericht antwoord = (ZoekPersoonAntwoordBericht) BrpBerichtFactory.SINGLETON.getBericht(
            "<zoekPersoonAntwoord xmlns=\"http://www.moderniseringgba.nl/Migratie/0001\">" 
               + "<status>Ok</status>" 
               + "<gevondenPersonen>" 
                   + "<gevondenPersoon>" 
                       + "<aNummer>8172387435</aNummer>" 
                       + "<bijhoudingsgemeente>1900</bijhoudingsgemeente>" 
                   + "</gevondenPersoon>" 
                   + "<gevondenPersoon>" 
                       + "<aNummer>8172387436</aNummer>" 
                       + "<bijhoudingsgemeente>1900</bijhoudingsgemeente>" 
                   + "</gevondenPersoon>" 
               + "</gevondenPersonen>" 
           + "</zoekPersoonAntwoord>");
        //@formatter:on

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", ii01Bericht);
        parameters.put("zoekPersoonBuitenGemeenteAntwoordBericht", antwoord);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) result.get("if01Bericht");
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("U", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals("0000", if01Bericht.getHeader(Lo3HeaderVeld.GEMEENTE));
    }
}
