/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.ExpressieType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: maels
 * Date: 19-11-13
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class ErrorHandlerTest {

    @Test
    public final void testGetallen() {
        //testExp("1a", ParserFoutCode.INCORRECT_GETAL, 0);
    }

    @Test
    public final void testStrings() {
        testExp("\"", ParserFoutCode.SYNTAX_ERROR, 2);
        testExp("\"teststring", ParserFoutCode.SYNTAX_ERROR, 12);
    }

    @Test
    public final void testDatumWaarden() {
        testExp("1970/2/0", ParserFoutCode.FOUT_IN_DATUM_INCORRECT_DAGNUMMER, 7);
        testExp("1970/2/29", ParserFoutCode.FOUT_IN_DATUM_INCORRECT_DAGNUMMER, 7);
        testExp("1970/13/1", ParserFoutCode.FOUT_IN_DATUM_INCORRECTE_MAAND, 5);
        testExp("1970/0/1", ParserFoutCode.FOUT_IN_DATUM_INCORRECTE_MAAND, 5);
        testExp("1970/2/", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("1970/", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1970/?/10", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("1970/2/", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("1970/", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1550/10/10", ParserFoutCode.FOUT_IN_DATUM_INCORRECT_JAARTAL, 0);
        testExp("4550/10/10", ParserFoutCode.FOUT_IN_DATUM_INCORRECT_JAARTAL, 0);
    }

    @Test
    public final void testPeriodes() {
    }

    @Test
    public final void testAttributen() {
        testExp("bla.geboorte.datum", ParserFoutCode.IDENTIFIER_ONBEKEND, 0);
        testExp("persoon.geblabla.foo", ParserFoutCode.SYNTAX_ERROR, 8);
    }

    @Test
    public final void testVariabelen() {

    }

    @Test
    public final void testClosures() {
        testExp("x + 2 = 12 WAARBIJ", ParserFoutCode.SYNTAX_ERROR, 18);
        testExp("x + 2 = 12 WAARBIJ x", ParserFoutCode.SYNTAX_ERROR, 20);
        testExp("x + 2 = 12 WAARBIJ x =", ParserFoutCode.SYNTAX_ERROR, 22);

    }

    @Test
    public final void testAttribuutCollecties() {
    }

    @Test
    public final void testRekenOperatoren() {
        testExp("-", ParserFoutCode.SYNTAX_ERROR, 1);
        testExp("+", ParserFoutCode.SYNTAX_ERROR, 0);
        testExp("10+WAAR", ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT, 3);
        testExp("1970/03/01 + \"a\"", ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT, 13);
        testExp("10+", ParserFoutCode.SYNTAX_ERROR, 3);
        testExp("10+3-", ParserFoutCode.SYNTAX_ERROR, 5);
    }

    @Test
    public final void testLijsten() {
        testExp("{10, 20", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("{10 20", ParserFoutCode.SYNTAX_ERROR, 4);
    }

    @Test
    public final void testBooleanOperatoren() {
        testExp("WAAR OF 10", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 8);
        testExp("20 OF 10", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 0);
        testExp("WAAR EN 10", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 8);
        testExp("\"a\" EN WAAR", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 0);
        testExp("NIET 5", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 5);
        testExp("NIET", ParserFoutCode.SYNTAX_ERROR, 4);
        testExp("WAAR OF", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("ONWAAR EN", ParserFoutCode.SYNTAX_ERROR, 9);
        testExp("NIET 1970/10/05", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 5);
    }

    @Test
    public final void testVergelijkingsOperatoren() {
        testExp("10 = WAAR", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 5);
        testExp("ONWAAR = \"xyz\"", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 9);
        testExp("1 < WAAR", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 4);
        testExp("\"a\" <= 10", ParserFoutCode.STRING_EXPRESSIE_VERWACHT, 7);
        testExp("10 <> 1980/10/1", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 6);
        testExp("geboorte.datum = \"TEST\"", ParserFoutCode.DATUM_EXPRESSIE_VERWACHT, 17);
    }

    @Test
    public final void testFunctieAanroepen() {
        testExp("VANDAAG(10, 5)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 0);
        testExp("MAAND(geboorte.land_gebied)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 0);
        testExp("AANTAL(10)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 0);
        testExp("AANTAL({10}, {20})", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 0);
        testExp("ALS(10, 1, 2)", ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, 0);
        testExp("ER_IS(KINDEREN, k, WAAR)", ParserFoutCode.SYNTAX_ERROR, 14);
        testExp("MAP(10)", ParserFoutCode.SYNTAX_ERROR, 6);
    }

    @Test
    public final void testFoutmeldingen() {
        ParserFout f = new ParserFout(ParserFoutCode.FOUT_IN_DATUM_INCORRECT_JAARTAL, "t", 10);
        Assert.assertEquals("Fout in datum: incorrect jaartal@10", f.toString());
        f = new ParserFout(ParserFoutCode.ONBEKENDE_FOUT, "t", 2);
        Assert.assertEquals("Onbekende fout@2", f.toString());
        f = new ParserFout(ParserFoutCode.ATTRIBUUT_ONBEKEND, "t", 4);
        Assert.assertEquals("Attribuut onbekend@4", f.toString());
        f = new ParserFout(ParserFoutCode.SYNTAX_ERROR, "t", 6);
        Assert.assertEquals("Syntax error@6", f.toString());
        f = new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, "t", 8);
        Assert.assertEquals("Fout in functieaanroep@8", f.toString());
        f = new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, null, 8);
        Assert.assertEquals("Fout in functieaanroep", f.toString());
    }

    private void testExp(final String input, final ParserFoutCode foutCode, final int foutPositie) {
        final int foutPositiePlushaakjes = foutPositie + 1;
        final ParserResultaat pr = BRPExpressies.parse(input);
        Assert.assertNotNull(pr);
        Assert.assertFalse("Failing expression expected", pr.succes());
        Assert.assertEquals(ExpressieType.ONBEKEND_TYPE, pr.getType());
        final ParserFout f = new ParserFout(foutCode, input, foutPositiePlushaakjes);
        Assert.assertEquals(f.toString(), pr.getFoutmelding());
        final ParserFout fout = pr.getFout();
        Assert.assertNotNull(fout);
        Assert.assertEquals("Verkeerde foutcode", foutCode, fout.getFoutCode());
        Assert.assertEquals("Foutpositie niet correct", foutPositiePlushaakjes, fout.getPositie());
    }
}
