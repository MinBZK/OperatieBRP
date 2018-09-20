/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.symbols.Attributes;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test de klasse ParserImpl.
 */
@SuppressWarnings("MagicNumber")
public class ParserImplTest {

//    @Test
//    public final void test() {
//        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzerImpl();
//        Parser parser = new ParserImpl();
//
//        //TokenStack tokens = lexicalAnalyzer.tokenize("persoon.bsn");
//        TokenStack tokens = lexicalAnalyzer.tokenize("persoon.nationaliteit[1].reden_verlies");
//        Assert.assertNotNull(tokens);
//
//        System.out.println(tokens.toString());
//
//        ParserResultaat pr = parser.parse(tokens);
//        System.out.println(pr.getExpressie());
//        System.out.println(pr.getErrors());
//    }

    @Test
    public final void testGetallen() {
        testHappy("100", "#100");
        testHappy("      120", "#120");
        testHappy("10      ", "#10");
        testHappy("      200      ", "#200");
        testHappy("12345678", "#12345678");
        testHappy("0", "#0");
        testHappy("00", "#0");
        testHappy("0100", "#100");

        //testUnhappyLexically("1a");
        testUnhappyParser("1a", ParserFoutCode.INCORRECT_GETAL, 1);
    }

    @Test
    public final void testNullWaarden() {
        testHappy("ONBEKEND", "^");
        testHappy("GEBOORTE.DATUM = ONBEKEND", "($[PERSOON<Persoon>.GEBOORTE.DATUM]=^)");
        testHappy("GEBOORTE.DATUM <> ONBEKEND", "($[PERSOON<Persoon>.GEBOORTE.DATUM]<>^)");
    }

    @Test
    public final void testStrings() {
        testHappy("\"\"", "\"\"");
        testHappy("\"string\"", "\"string\"");
        testHappy("\" \"", "\" \"");

        testUnhappyParser("\"", ParserFoutCode.AANHALINGSTEKENS_ONTBREKEN, 1);
        testUnhappyParser("\"teststring", ParserFoutCode.AANHALINGSTEKENS_ONTBREKEN, 11);
    }

    @Test
    public final void testBooleanWaarden() {
        testHappy("TRUE", "TRUE");
        testHappy("FALSE", "FALSE");
    }

    @Test
    public final void testDatumWaarden() {
        testHappy("#1970-2-1#", "#1970-FEB-01#");
        testHappy("#1982-12#", "#1982-DEC-00#");
        testHappy("#1970-JAN-2#", "#1970-JAN-02#");
        testHappy("#1971-FEBRUARI-2#", "#1971-FEB-02#");
        testHappy("#1972-AUG#", "#1972-AUG-00#");
        testHappy("#1973-DECEMBER#", "#1973-DEC-00#");
        testHappy("#1924#", "#1924-???-00#");

        testUnhappyParser("#1970-2-1", ParserFoutCode.FOUT_IN_DATUM_AFSLUITINGSTEKEN_ONTBREEKT, 9);
        testUnhappyParser("#1970-2-", ParserFoutCode.FOUT_IN_DATUM_DAG_ONTBREEKT, 8);
        testUnhappyParser("#1970-", ParserFoutCode.FOUT_IN_DATUM_MAAND_ONTBREEKT, 6);
        testUnhappyParser("#1970", ParserFoutCode.FOUT_IN_DATUM_AFSLUITINGSTEKEN_ONTBREEKT, 5);
        testUnhappyParser("#1970-2-#", ParserFoutCode.FOUT_IN_DATUM_DAG_ONTBREEKT, 8);
        testUnhappyParser("#1970-#", ParserFoutCode.FOUT_IN_DATUM_MAAND_ONTBREEKT, 6);
        testUnhappyParser("#", ParserFoutCode.FOUT_IN_DATUM_JAARTAL_ONTBREEKT, 1);
    }

    @Test
    public final void testAttributen() {
        testHappy("BSN", "$[PERSOON<Persoon>.IDENTIFICATIENUMMERS.BURGERSERVICENUMMER]");
        testHappy("IDENTIFICATIENUMMERS.BURGERSERVICENUMMER",
                "$[PERSOON<Persoon>.IDENTIFICATIENUMMERS.BURGERSERVICENUMMER]");
        testHappy("PERSOON.GEBOORTE.WOONPLAATS", "$[PERSOON<Persoon>.GEBOORTE.WOONPLAATS]");
        testHappy("GEBOORTE.DATUM", "$[PERSOON<Persoon>.GEBOORTE.DATUM]");

        testUnhappyParser("P.GEBOORTE.DATUM", ParserFoutCode.IDENTIFIER_ONBEKEND, 0);
        testUnhappyParser("P.LAND_AANVANG", ParserFoutCode.IDENTIFIER_ONBEKEND, 0);
        testUnhappyParser("PERSOON.GEBOORTE.FEESTMUTS", ParserFoutCode.IDENTIFIER_ONBEKEND, 0);
    }

    @Test
    public final void testGeraakteAttributen() {
        ParserResultaat pr = BRPExpressies.parse("geboorte.datum > overlijden.datum en bsn=11111111");
        Assert.assertNotNull("Parser result == null", pr);
        Assert.assertTrue("Error returned: " + pr.getFoutmelding(), pr.succes());

        Expressie expressie = pr.getExpressie();

        Assert.assertTrue(expressie.includes(Attributes.PERSOON_GEBOORTE_DATUM));
        Assert.assertTrue(expressie.includes(Attributes.PERSOON_OVERLIJDEN_DATUM));
        Assert.assertTrue(expressie.includes(Attributes.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER));
        Assert.assertFalse(expressie.includes(Attributes.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER));
    }

    @Test
    public final void testVariabelen() {
        testHappy("persoon", "%PERSOON<Persoon>");
        testUnhappyParser("x", ParserFoutCode.IDENTIFIER_ONBEKEND, 0);
    }

    @Test
    public final void testGeindexeerdeAttributen() {
        testHappy("persoon.nationaliteiten[1].reden_verlies", "$[PERSOON<Persoon>.NATIONALITEITEN[#1]REDEN_VERLIES]");
        testHappy("nationaliteiten[2].reden_verlies", "$[PERSOON<Persoon>.NATIONALITEITEN[#2]REDEN_VERLIES]");
        testHappy("voornamen[1].volgnummer", "$[PERSOON<Persoon>.VOORNAMEN[#1]VOLGNUMMER]");
        testHappy("voornamen[1].naam", "$[PERSOON<Persoon>.VOORNAMEN[#1]NAAM]");
        testHappy("persoon.voornamen[1].naam", "$[PERSOON<Persoon>.VOORNAMEN[#1]NAAM]");
        testHappy("adressen[1].datum_aanvang_adreshouding > #2000-OKT-10#",
                "($[PERSOON<Persoon>.ADRESSEN[#1]DATUM_AANVANG_ADRESHOUDING]>#2000-OKT-10#)");
        testHappy("adressen[]", "$[PERSOON<Persoon>.ADRESSEN[]]");
        testHappy("persoon.nationaliteiten[]", "$[PERSOON<Persoon>.NATIONALITEITEN[]]");

        testUnhappyParser("persoon.nationaliteiten[1].snor", ParserFoutCode.ATTRIBUUT_VERWACHT, 27);
        testUnhappyParser("persoon.nationaliteiten[1].bsn", ParserFoutCode.ATTRIBUUT_ONBEKEND, 27);
        testUnhappyParser("persoon.nationaliteiten[TRUE].reden_verlies",
                ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 24);
        testUnhappyParser("persoon.nationaliteiten", ParserFoutCode.INDEX_VERWACHT, 23);
        testUnhappyParser("identificatienummers.burgerservicenummer[1].reden_verlies",
                ParserFoutCode.ATTRIBUUT_IS_NIET_GEINDEXEERD, 41);
        testUnhappyParser("identificatienummers.burgerservicenummer[]",
                ParserFoutCode.ATTRIBUUT_IS_NIET_GEINDEXEERD, 41);
    }

    @Test
    public final void testRekenOperatoren() {
        testHappy("-10", "(-#10)");
        testHappy("-0", "(-#0)");
        testHappy("10+20", "(#10+#20)");
        testHappy("10-20", "(#10-#20)");
        testHappy("10*20", "(#10*#20)");
        testHappy("10/20", "(#10/#20)");
        testHappy("1+2+3", "(#1+(#2+#3))");
        testHappy("1*2*3", "(#1*(#2*#3))");
        testHappy("1*2+3", "((#1*#2)+#3)");
        testHappy("1+2*3", "(#1+(#2*#3))");
        testHappy("1+2-3+4", "(#1+(#2-(#3+#4)))");
        testHappy("1*2+3*4", "((#1*#2)+(#3*#4))");
        testHappy("1*(2+3)*4", "(#1*((#2+#3)*#4))");
        testHappy("1*2/3*4", "(#1*(#2/(#3*#4)))");

        testUnhappyParser("-", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 1);
        testUnhappyParser("+", ParserFoutCode.SYNTAX_ERROR, 0);
        testUnhappyParser("10+", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 3);
        testUnhappyParser("5*", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 2);
        testUnhappyParser("10+3-", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 3);
    }

    @Test
    public final void testLijsten() {
        testHappy("{10, 20}", "{#10,#20}");
        testHappy("{ }", "{}");
        testHappy("{\"foo\",  \"bar\"  }", "{\"foo\",\"bar\"}");
        testHappy("10 in {10, 20}", "(#10.IN.{#10,#20})");
        testHappy("persoon.voornamen[1].naam in {\"foo\",  \"bar\"  }",
                "($[PERSOON<Persoon>.VOORNAMEN[#1]NAAM].IN.{\"foo\",\"bar\"})");

        testUnhappyParser("{10, 20", ParserFoutCode.LIJST_NIET_AFGESLOTEN, 7);
        testUnhappyParser("{10 20", ParserFoutCode.FOUT_IN_LIJST, 4);
    }

    @Test
    public final void testBooleanOperatoren() {
        testHappy("TRUE EN FALSE", "(TRUE.EN.FALSE)");
        testHappy("TRUE OF FALSE", "(TRUE.OF.FALSE)");
        testHappy("NIET TRUE EN FALSE", "((!TRUE).EN.FALSE)");
        testHappy("NIET (TRUE EN FALSE)", "(!(TRUE.EN.FALSE))");

        testUnhappyParser("TRUE EN 10", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 8);
        testUnhappyParser("NIET 5", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 5);
    }

    @Test
    public final void testVergelijkingsOperatoren() {
        testHappy("10 = 20", "(#10=#20)");
        testHappy("10 > 20", "(#10>#20)");
        testHappy("15 < 25", "(#15<#25)");
        testHappy("10 <> 20", "(#10<>#20)");
        testHappy("GEBOORTE.DATUM = #1970-DEC-10#", "($[PERSOON<Persoon>.GEBOORTE.DATUM]=#1970-DEC-10#)");
        testHappy("(1 = 2)", "(#1=#2)");
        testHappy("(1 = 2) EN (3 < 4)", "((#1=#2).EN.(#3<#4))");

        testUnhappyParser("GEBOORTE.DATUM = \"TEST\"", ParserFoutCode.DATUM_EXPRESSIE_VERWACHT, 17);
        testUnhappyParser("1 < TRUE", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 4);
    }

    @Test
    public final void testFunctieAanroepen() {
        testHappy("NU()", "@NU()");
        testHappy("GEDEFINIEERD(BSN)", "@GEDEFINIEERD($[PERSOON<Persoon>.IDENTIFICATIENUMMERS.BURGERSERVICENUMMER])");
        testHappy("IN_ONDERZOEK(IDENTIFICATIENUMMERS.ANUMMER)",
                "@IN_ONDERZOEK($[PERSOON<Persoon>.IDENTIFICATIENUMMERS.ADMINISTRATIENUMMER])");
        testHappy("JAAR(#1970-2-1#)", "@JAAR(#1970-FEB-01#)");
        testHappy("JAAR(#1980-JAN#)", "@JAAR(#1980-JAN-00#)");
        testHappy("JAAR(PERSOON.GEBOORTE.DATUM)", "@JAAR($[PERSOON<Persoon>.GEBOORTE.DATUM])");
        testHappy("MAAND(#1945-5-5#)", "@MAAND(#1945-MEI-05#)");
        testHappy("MAAND(#1965-AUG#)", "@MAAND(#1965-AUG-00#)");
        testHappy("MAAND(GEBOORTE.DATUM)", "@MAAND($[PERSOON<Persoon>.GEBOORTE.DATUM])");
        testHappy("AANTAL(ADRESSEN[]) = 2", "(@AANTAL($[PERSOON<Persoon>.ADRESSEN[]])=#2)");
        testHappy("AANTAL(NATIONALITEITEN[])", "@AANTAL($[PERSOON<Persoon>.NATIONALITEITEN[]])");
        testHappy("KINDEREN(PERSOON)", "@KINDEREN(%PERSOON<Persoon>)");
        testHappy("KINDEREN()", "@KINDEREN(%PERSOON<Persoon>)");
        testHappy("ALLE(KINDEREN(PERSOON),K,TRUE)", "@ALLE(@KINDEREN(%PERSOON<Persoon>),%K<Persoon>,TRUE)");
        testHappy("ALLE(KINDEREN(),K,FALSE)", "@ALLE(@KINDEREN(%PERSOON<Persoon>),%K<Persoon>,FALSE)");
        testHappy("ER_IS(KINDEREN(PERSOON),K,TRUE)", "@ER_IS(@KINDEREN(%PERSOON<Persoon>),%K<Persoon>,TRUE)");
        testHappy("ER_IS(KINDEREN(),K,FALSE)", "@ER_IS(@KINDEREN(%PERSOON<Persoon>),%K<Persoon>,FALSE)");
        testHappy("ER_IS(OUDERS(),K,FALSE)", "@ER_IS(@OUDERS(%PERSOON<Persoon>),%K<Persoon>,FALSE)");
        testHappy("PARTNERS()", "@PARTNERS(%PERSOON<Persoon>)");
        testHappy("HUWELIJKEN()", "@HUWELIJKEN(%PERSOON<Persoon>,@NU())");
        testHappy("HUWELIJKEN(#1970-08-02#)", "@HUWELIJKEN(%PERSOON<Persoon>,#1970-AUG-02#)");
        testHappy("er_is(huwelijken(), h, h.datum_aanvang > #1960-JAN-1#)",
                "@ER_IS(@HUWELIJKEN(%PERSOON<Persoon>,@NU()),%H<Huwelijk>," +
                        "($[H<Huwelijk>.DATUM_AANVANG]>#1960-JAN-01#))");
        testHappy("er_is(huwelijken(#1970-08-02#), h, h.datum_aanvang > #1960-JAN-1#)",
                "@ER_IS(@HUWELIJKEN(%PERSOON<Persoon>,#1970-AUG-02#),%H<Huwelijk>," +
                        "($[H<Huwelijk>.DATUM_AANVANG]>#1960-JAN-01#))");

        testUnhappyParser("NU(10)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 3);
        testUnhappyParser("GEDEFINIEERD(10)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 13);
        testUnhappyParser("MAAND(GEBOORTE.LAND)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 6);
        testUnhappyParser("AANTAL(10)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 7);
        testUnhappyParser("ER_IS(KINDEREN, K, TRUE)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 14);
    }

    private void testHappy(final String input, final String formalOutput) {
        ParserResultaat pr = BRPExpressies.parse(input);
        Assert.assertNotNull("Parser result == null", pr);
        Assert.assertTrue("Error returned: " + pr.getFoutmelding(), pr.succes());

        Expressie expressie = pr.getExpressie();
        Assert.assertNotNull("Expressie == null", expressie);

        Assert.assertEquals("Output differs", formalOutput, expressie.alsFormeleString());
    }

    private void testUnhappyParser(final String input, final ParserFoutCode foutCode, final int foutPositie) {
        ParserResultaat pr = BRPExpressies.parse(input);
        Assert.assertNotNull(pr);
        Assert.assertFalse("Failing expression expected", pr.succes());
        ParserFout fout = pr.getFout();
        Assert.assertNotNull(fout);
        Assert.assertEquals("Verkeerde foutcode", foutCode, fout.getFoutCode());
        Assert.assertEquals("Foutpositie niet correct", foutPositie, fout.getToken().getPosition());
    }
}
