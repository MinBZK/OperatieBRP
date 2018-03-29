/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import nl.bzk.brp.domain.expressie.util.ExpressietaalTestPersoon;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test de default parser klasse.
 */
public class ParserTest {

    @Test
    public void testGetallen() throws ExpressieException {
        testHappy("100", "100");
        testHappy("      120", "120");
        testHappy("10      ", "10");
        testHappy("      200      ", "200");
        testHappy("12345678", "12345678");
        testHappy("0", "0");
        testHappy("00", "0");
        testHappy("0100", "100");
        testHappy("-100", "-100");
    }

    @Test
    public void testNullWaarden() throws ExpressieException {
        testHappy("NULL", "NULL");
        testHappy("Persoon.Geboorte.Datum = NULL", "Persoon.Geboorte.Datum = NULL");
        testHappy("Persoon.Geboorte.Datum <> NULL", "Persoon.Geboorte.Datum <> NULL");
    }

    @Test
    public void testStrings() throws ExpressieException {
        testHappy("\"\"", "\"\"");
        testHappy("\"string\"", "\"string\"");
        testHappy("\" \"", "\" \"");
    }

    @Test
    public void testBooleanWaarden() throws ExpressieException {
        testHappy("WAAR", "WAAR");
        testHappy("ONWAAR", "ONWAAR");
    }

    @Test
    public void testDatumWaarden() throws ExpressieException {
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
    public void testPeriodes() throws ExpressieException {
        testHappy("^10/1/1", "^10/1/1");
        testHappy("^-10/0/-5", "^-10/0/-5");
        testHappy("^0/0/-1", "^0/0/-1");
        testHappy("^0/0/1 + ^0/1/0", "^0/0/1 + ^0/1/0");
        testHappy("^0/0/1 - ^0/1/0", "^0/0/1 - ^0/1/0");
        testHappy("1980/3/1 + ^0/1/0", "1980/03/01 + ^0/1/0");
    }

    @Test
    public void testAttributen() throws ExpressieException {
        testHappy("BSN", "BSN");
        testHappy("Persoon.Identificatienummers.Burgerservicenummer", "Persoon.Identificatienummers.Burgerservicenummer");
        testHappy("Persoon.Geboorte.Woonplaatsnaam", "Persoon.Geboorte.Woonplaatsnaam");
        testHappy("Persoon.Geboorte.Datum", "Persoon.Geboorte.Datum");
        testHappy("Persoon.Bijhouding.PartijCode A= 74301", "Persoon.Bijhouding.PartijCode A= 74301");
        testHappy("Persoon.Bijhouding.PartijCode E= 74301", "Persoon.Bijhouding.PartijCode E= 74301");
        testHappy("Persoon.Bijhouding.PartijCode AIN {74301, 75301, 76201}", "Persoon.Bijhouding.PartijCode AIN {74301, 75301, 76201}");
        testHappy("Persoon.Bijhouding.PartijCode EIN {74301, 75301, 76201}", "Persoon.Bijhouding.PartijCode EIN {74301, 75301, 76201}");
    }

    @Test
    public void testGeraakteAttributen() throws ExpressieException {
        // final Expressie pr = BRPExpressies.parse("geboorte.datum > overlijden.datum EN bsn=11111111");
//        Assert.assertNotNull("Parser result == null", pr);
//        Assert.assertTrue("Error returned: " + pr.getFoutmelding(), pr.succes());
    }

    @Test
    public void testClosures() throws ExpressieException {
        testHappy("x+2=12 WAARBIJ x=10", "(x + 2 = 12) WAARBIJ x = 10");
        testHappy("(y + y WAARBIJ y = (x+2 WAARBIJ x = 1))",
                "(y + y) WAARBIJ y = (x + 2) WAARBIJ x = 1");
        testHappy("x < 2 WAARBIJ x = 4", "(x < 2) WAARBIJ x = 4");
        testHappy("x+y=12 WAARBIJ x=10,y=2", "(x + y = 12) WAARBIJ x = 10, y = 2");
        testHappy("x+y=z-x WAARBIJ x=10,y=2,z=25", "(x + y = z - x) WAARBIJ x = 10, y = 2, z = 25");
    }

    @Test
    public void testAttribuutCollecties() throws ExpressieException {
        testHappy("Adressen", "Adressen");
        testHappy("persoon.Nationaliteiten", "Nationaliteiten");
        testHappy("FILTER(persoon.Adressen, a, a.Postcode = \"1234AB\")", "FILTER(Adressen, a, a.Postcode = "
                + "\"1234AB\")");
    }

    @Test
    public void testRekenOperatoren() throws ExpressieException {
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
    public void testLijsten() throws ExpressieException {
        testHappy("{10, 20}", "{10, 20}");
        testHappy("{ }", "{}");
        testHappy("{\"foo\",  \"bar\"  }", "{\"foo\", \"bar\"}");
        //testHappy("10 EIN {10, 20}", "10 EIN {10, 20}");
        testHappy("{1,2} + {3,4}", "{1, 2} + {3, 4}");
        testHappy("{}+{}", "{} + {}");
    }

    @Test
    public void testBooleanOperatoren() throws ExpressieException {
        testHappy("WAAR EN ONWAAR", "WAAR EN ONWAAR");
        testHappy("WAAR OF ONWAAR", "WAAR OF ONWAAR");
        testHappy("NIET WAAR EN ONWAAR", "NIET WAAR EN ONWAAR");
        testHappy("NIET (WAAR EN ONWAAR)", "NIET (WAAR EN ONWAAR)");
        testHappy("WAAR OF WAAR EN ONWAAR", "WAAR OF WAAR EN ONWAAR");
        testHappy("WAAR OF WAAR EN ONWAAR OF ONWAAR", "WAAR OF (WAAR EN ONWAAR OF ONWAAR)");
        testHappy("(WAAR OF WAAR) EN (ONWAAR OF ONWAAR)", "(WAAR OF WAAR) EN (ONWAAR OF ONWAAR)");
    }

    @Test
    public void testVergelijkingsOperatoren() throws ExpressieException {
        testHappy("10 = 20", "10 = 20");
        testHappy("10 > 20", "10 > 20");
        testHappy("10 >= 20", "10 >= 20");
        testHappy("15 < 25", "15 < 25");
        testHappy("15 <= 25", "15 <= 25");
        testHappy("10 <> 20", "10 <> 20");
        testHappy("(1 = 2)", "1 = 2");
        testHappy("(1 = 2) EN (3 < 4)", "1 = 2 EN 3 < 4");
        testHappy("\"Regex\" =% \"Reg*\"", "\"Regex\" =% \"Reg*\"");
    }

    /**
     * Unrecognized tokens worden in de Errorlistener van de Lexer afgevangen.
     * Dit zijn tokens die niet in de grammer voorkomen.
     */
    @Test
    public void testUnrecognizedTokens() throws ExpressieException {
        TestUtils.assertExceptie("****#;*******a*", "token recognition error at: '*'");
    }

    @Test
    public void testFunctieAanroepen() throws ExpressieException {
        testHappy("VANDAAG()", "VANDAAG(0)");
        testHappy("VANDAAG(5)", "VANDAAG(5)");
        testHappy("JAAR(1970/2/1)", "JAAR(1970/02/01)");
        testHappy("JAAR(1980/JAN/?)", "JAAR(1980/01/?)");
        testHappy("JAAR(Persoon.Geboorte.Datum)", "JAAR(Persoon.Geboorte.Datum)");
        testHappy("MAAND(1945/5/5)", "MAAND(1945/05/05)");
        testHappy("MAAND(1965/AUG/?)", "MAAND(1965/08/?)");
        testHappy("MAAND(Persoon.Geboorte.Datum)", "MAAND(Persoon.Geboorte.Datum)");
        testHappy("AANTAL(Adressen) = 2", "AANTAL(Adressen) = 2");
        testHappy("AANTAL(Nationaliteiten)", "AANTAL(Nationaliteiten)");
        testHappy("MAP({1},x,x+10)", "MAP({1}, x, x + 10)");
        testHappy("MAP({1,{2}},x,x+10)", "MAP({1, {2}}, x, x + 10)");
        testHappy("MAP(FILTER(Nationaliteiten, n, n.Nationaliteit = 339), m, m.Nationaliteit)",
                "MAP(FILTER(Nationaliteiten, n, n.Nationaliteit = 339), m, m.Nationaliteit)");
        //        testHappy("map(voornamen, l, map(l, n, n.naam))",
        //                "@MAP([persoon<Persoon>.voornamen],%L,@MAP(%L,%N,%NAAM))");
        //        testHappy("FILTER(PLATTE_LIJST(indicaties), i, i.soort = \"Verstrekkingsbeperking?\")",
        //                "FILTER(PLATTE_LIJST(persoon.indicaties), i, i.soort = \"Verstrekkingsbeperking?\")");
    }

    @Test
    public void testOngeldigKeyword() {
        TestUtils.assertExceptie("ONGELDIG()", "Syntax error \"no viable alternative at input 'ONGELDIG('\" op positie: 9");
        TestUtils.assertExceptie("x.y.z", "Ongeldige elementexpressie: x.y.z");
        TestUtils.assertExceptie("\"abc\" IN% {\"abc\", \"de*\", \"kl?\", \"\"}", "token recognition error at: '%'");
        TestUtils.assertExceptie("\"abc\" FUNCTIEX {\"abc\", \"de*\", \"kl?\", \"\"}", "Syntax error \"mismatched input 'FUNCTIEX' expecting ')'\""
                + " op positie: 7");
    }

    @Test
    public void testOngeldigEindeRegel() throws ExpressieException {
        TestUtils.assertExceptie("WAAR ONWAAR", "Syntax error \"extraneous input 'ONWAAR' expecting ')'\" op positie: 6");
    }

    @Test
    public void testAttribuutcodes() throws ExpressieException {
        testHappy("[geboorte.datum]", "[geboorte.datum]");
    }

    @Test
    public void testPersoon() throws ExpressieException {
        testHappy("persoon", "persoon");
        TestUtils.testEvaluatie("persoon", "@Persoon", ExpressietaalTestPersoon.PERSOONSLIJST);
        final Expressie persoon = TestUtils.evalueer("persoon", ExpressietaalTestPersoon.PERSOONSLIJST);
        Assert.assertTrue(persoon instanceof MetaObjectLiteral);
    }

    @Test
    public void testCreatieInitialContext() throws ExpressieException {
        Assert.assertTrue(ExpressieParser.parse("WAAR").alsBoolean());
    }

    private void testHappy(final String input, final String formalOutput) throws ExpressieException {
        Expressie expressie = ExpressieParser.parse(input);
        Assert.assertNotNull("Expressie == null", expressie);
        Assert.assertEquals("Output differs", formalOutput, expressie.toString());
    }
}
