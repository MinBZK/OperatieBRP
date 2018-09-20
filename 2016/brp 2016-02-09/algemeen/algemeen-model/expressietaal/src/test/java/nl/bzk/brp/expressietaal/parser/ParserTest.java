/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.Expressie;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test de default parser klasse.
 */
@SuppressWarnings("MagicNumber")
public class ParserTest {

    @Test
    public final void testGetallen() {
        testHappy("100", "100");
        testHappy("      120", "120");
        testHappy("10      ", "10");
        testHappy("      200      ", "200");
        testHappy("12345678", "12345678");
        testHappy("0", "0");
        testHappy("00", "0");
        testHappy("0100", "100");
        //testHappy("-100", "-100");
    }

    @Test
    public final void testNullWaarden() {
        testHappy("NULL", "NULL");
        testHappy("geboorte.datum = NULL", "persoon.geboorte.datum = NULL");
        testHappy("geboorte.datum <> NULL", "persoon.geboorte.datum <> NULL");
    }

    @Test
    public final void testStrings() {
        testHappy("\"\"", "\"\"");
        testHappy("\"string\"", "\"string\"");
        testHappy("\" \"", "\" \"");
    }

    @Test
    public final void testBooleanWaarden() {
        testHappy("WAAR", "WAAR");
        testHappy("ONWAAR", "ONWAAR");
    }

    @Test
    public final void testDatumWaarden() {
        testHappy("1970/2/1", "1970/02/01");
        testHappy("1982/12/?", "1982/12/?");
        testHappy("1970/JAN/2", "1970/01/02");
        testHappy("1971/FEBRUARI/2", "1971/02/02");
        testHappy("1972/AUG/?", "1972/08/?");
        testHappy("1973/DECEMBER/?", "1973/12/?");
        testHappy("1924/?/?", "1924/?/?");
        testHappy("1972/FEBRUARI/29", "1972/02/29");

    }

    @Test
    public final void testPeriodes() {
        testHappy("^10/1/1", "^10/1/1");
        testHappy("^10/2", "^10/2/0");
        testHappy("^15/0", "^15/0/0");
        testHappy("^29", "^29/0/0");
        testHappy("^-10/0/-5", "^-10/0/-5");
        testHappy("^0/0/-1", "^0/0/-1");
        testHappy("^0/0/1 + ^0/1/0", "^0/0/1 + ^0/1/0");
        testHappy("^0/0/1 - ^0/1/0", "^0/0/1 - ^0/1/0");
        testHappy("1980/3/1 + ^0/1/0", "1980/03/01 + ^0/1/0");
    }

    @Test
    public final void testAttributen() {
        testHappy("bsn", "persoon.identificatienummers.burgerservicenummer");
        testHappy("identificatienummers.burgerservicenummer",
                "persoon.identificatienummers.burgerservicenummer");
        testHappy("persoon.geboorte.woonplaatsnaam", "persoon.geboorte.woonplaatsnaam");
        testHappy("geboorte.datum", "persoon.geboorte.datum");
        testHappy("$geboorte.datum", "$persoon.geboorte.datum");
    }

    @Test
    public final void testGeraakteAttributen() {
        final ParserResultaat pr = BRPExpressies.parse("geboorte.datum > overlijden.datum EN bsn=11111111");
        Assert.assertNotNull("Parser result == null", pr);
        Assert.assertTrue("Error returned: " + pr.getFoutmelding(), pr.succes());
    }

    @Test
    public final void testVariabelen() {
        testHappy("persoon", "persoon");
        testHappy("x", "x");
        testHappy("x+2", "x + 2");
        testHappy("x+2=12", "x + 2 = 12");
        testHappy("x<2", "x < 2");
        testHappy("JAAR(x)", "JAAR(x)");
    }

    @Test
    public final void testClosures() {
        testHappy("x+2=12 WAARBIJ x=10", "(x + 2 = 12) WAARBIJ x = 10");
        testHappy("(y + y WAARBIJ y = (x+2 WAARBIJ x = 1))",
                "(y + y) WAARBIJ y = (x + 2) WAARBIJ x = 1");
        testHappy("x < 2 WAARBIJ x = 4", "(x < 2) WAARBIJ x = 4");
        testHappy("x+y=12 WAARBIJ x=10,y=2", "(x + y = 12) WAARBIJ y = 2, x = 10");
        testHappy("x+y=z-x WAARBIJ x=10,y=2,z=25", "(x + y = z - x) WAARBIJ z = 25, y = 2, x = 10");
    }

    @Test
    public final void testAttribuutCollecties() {
        testHappy("adressen", "persoon.adressen");
        testHappy("persoon.nationaliteiten", "persoon.nationaliteiten");
        testHappy("FILTER(persoon.adressen, a, a.postcode = \"1234AB\")", "FILTER(persoon.adressen, a, a.postcode = "
            + "\"1234AB\")");
    }

    @Test
    public final void testRekenOperatoren() {
        testHappy("-10", "-10");
        testHappy("-DAG(1980/02/01)", "-DAG(1980/02/01)");
        testHappy("-0", "0");
        testHappy("10+20", "10 + 20");
        testHappy("10-20", "10 - 20");
        testHappy("10--20", "10 - -20");
        testHappy("1+2+3", "1 + (2 + 3)");
        testHappy("1+2-3+4", "1 + (2 - (3 + 4))");
    }

    @Test
    public final void testLijsten() {
        testHappy("{10, 20}", "{10, 20}");
        testHappy("{ }", "{}");
        testHappy("{\"foo\",  \"bar\"  }", "{\"foo\", \"bar\"}");
        testHappy("10 IN {10, 20}", "10 IN {10, 20}");
        testHappy("{1,2} + {3,4}", "{1, 2} + {3, 4}");
        testHappy("{}+{}", "{} + {}");
    }

    @Test
    public final void testBooleanOperatoren() {
        testHappy("WAAR EN ONWAAR", "WAAR EN ONWAAR");
        testHappy("WAAR OF ONWAAR", "WAAR OF ONWAAR");
        testHappy("NIET WAAR EN ONWAAR", "NIET WAAR EN ONWAAR");
        testHappy("NIET (WAAR EN ONWAAR)", "NIET (WAAR EN ONWAAR)");
        testHappy("WAAR OF WAAR EN ONWAAR", "WAAR OF WAAR EN ONWAAR");
        testHappy("WAAR OF WAAR EN ONWAAR OF ONWAAR", "WAAR OF (WAAR EN ONWAAR OF ONWAAR)");
        testHappy("(WAAR OF WAAR) EN (ONWAAR OF ONWAAR)", "(WAAR OF WAAR) EN (ONWAAR OF ONWAAR)");
    }

    @Test
    public final void testVergelijkingsOperatoren() {
        testHappy("10 = 20", "10 = 20");
        testHappy("10 > 20", "10 > 20");
        testHappy("10 >= 20", "10 >= 20");
        testHappy("15 < 25", "15 < 25");
        testHappy("15 <= 25", "15 <= 25");
        testHappy("10 <> 20", "10 <> 20");
        testHappy("(1 = 2)", "1 = 2");
        testHappy("(1 = 2) EN (3 < 4)", "1 = 2 EN 3 < 4");
        testHappy("\"Regex\" %= \"Reg*\"", "\"Regex\" %= \"Reg*\"");
    }

    @Test
    public final void testFunctieAanroepen() {
        testHappy("VANDAAG()", "VANDAAG(0)");
        testHappy("VANDAAG(5)", "VANDAAG(5)");
        testHappy("JAAR(1970/2/1)", "JAAR(1970/02/01)");
        testHappy("JAAR(1980/JAN/?)", "JAAR(1980/01/?)");
        testHappy("JAAR(persoon.geboorte.datum)", "JAAR(persoon.geboorte.datum)");
        testHappy("MAAND(1945/5/5)", "MAAND(1945/05/05)");
        testHappy("MAAND(1965/AUG/?)", "MAAND(1965/08/?)");
        testHappy("MAAND(geboorte.datum)", "MAAND(persoon.geboorte.datum)");
        testHappy("AANTAL(adressen) = 2", "AANTAL(persoon.adressen) = 2");
        testHappy("AANTAL(nationaliteiten)", "AANTAL(persoon.nationaliteiten)");
        testHappy("KINDEREN(persoon)", "KINDEREN(persoon)");
        testHappy("KINDEREN()", "KINDEREN(persoon)");
        testHappy("ALLE(KINDEREN(persoon),k,WAAR)", "ALLE(KINDEREN(persoon), k, WAAR)");
        testHappy("ALLE(KINDEREN(),k,ONWAAR)", "ALLE(KINDEREN(persoon), k, ONWAAR)");
        testHappy("ER_IS(KINDEREN(persoon),k,WAAR)", "ER_IS(KINDEREN(persoon), k, WAAR)");
        testHappy("ER_IS(KINDEREN(),k,ONWAAR)", "ER_IS(KINDEREN(persoon), k, ONWAAR)");
        testHappy("ER_IS(OUDERS(),k,ONWAAR)", "ER_IS(OUDERS(persoon), k, ONWAAR)");
        testHappy("PARTNERS()", "PARTNERS(persoon)");
        testHappy("HUWELIJKEN()", "HUWELIJKEN(persoon)");
        testHappy("MAP({1},x,x+10)", "MAP({1}, x, x + 10)");
        testHappy("RMAP({1,{2}},x,x+10)", "RMAP({1, {2}}, x, x + 10)");
        testHappy("MAP(FILTER(persoon.nationaliteiten, n, n.nationaliteit = 339), m, m.nationaliteit)",
                "MAP(FILTER(persoon.nationaliteiten, n, n.nationaliteit = 339), m, m.nationaliteit)");
        testHappy("\"M\" IN MAP(KINDEREN(), k, k.geslachtsaanduiding.geslachtsaanduiding)",
                "\"M\" IN MAP(KINDEREN(persoon), k, k.geslachtsaanduiding.geslachtsaanduiding)");
        //        testHappy("map(voornamen, l, map(l, n, n.naam))",
        //                "@MAP([persoon<Persoon>.voornamen],%L,@MAP(%L,%N,%NAAM))");
        //        testHappy("FILTER(PLATTE_LIJST(indicaties), i, i.soort = \"Verstrekkingsbeperking?\")",
        //                "FILTER(PLATTE_LIJST(persoon.indicaties), i, i.soort = \"Verstrekkingsbeperking?\")");
        testHappy("VIEW(persoon, 1980/01/01)", "VIEW(persoon, 1980/01/01)");
        testHappy("GERELATEERDE_BETROKKENHEDEN()", "GERELATEERDE_BETROKKENHEDEN(persoon, \"\", \"\", \"\")");
        testHappy("GERELATEERDE_BETROKKENHEDEN(persoon)", "GERELATEERDE_BETROKKENHEDEN(persoon, \"\", \"\", \"\")");
        testHappy("GERELATEERDE_BETROKKENHEDEN(persoon, \"R\")",
                "GERELATEERDE_BETROKKENHEDEN(persoon, \"R\", \"\", \"\")");
        testHappy("GERELATEERDE_BETROKKENHEDEN(\"R\")", "GERELATEERDE_BETROKKENHEDEN(persoon, \"R\", \"\", \"\")");
        testHappy("GERELATEERDE_BETROKKENHEDEN(persoon, \"R\", \"O\", \"E\")",
                "GERELATEERDE_BETROKKENHEDEN(persoon, \"R\", \"O\", \"E\")");
        testHappy("GERELATEERDE_BETROKKENHEDEN(\"R\", \"O\", \"E\")",
                "GERELATEERDE_BETROKKENHEDEN(persoon, \"R\", \"O\", \"E\")");
    }

    @Test
    public final void testAttribuutcodes() {
        testHappy("[geboorte.datum]", "[geboorte.datum]");
    }

    private void testHappy(final String input, final String formalOutput) {
        final ParserResultaat pr = BRPExpressies.parse(input);
        Assert.assertNotNull("Parser result == null", pr);
        Assert.assertTrue("Error returned: " + pr.getFoutmelding(), pr.succes());
        Assert.assertEquals("", pr.getFoutmelding());
        final Expressie expressie = pr.getExpressie();
        Assert.assertNotNull("Expressie == null", expressie);
        Assert.assertEquals("Output differs", formalOutput, expressie.toString());
    }
}
