/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFoutCode;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ErrorHandlerTest {

    @Test
    public final void testStrings() throws ExpressieException {
        testExp("\"", ParserFoutCode.SYNTAX_ERROR, 2);
        testExp("\"teststring", ParserFoutCode.SYNTAX_ERROR, 12);
    }

    @Test
    public final void testDatumWaarden() throws ExpressieException {
        testExp("1970/2/", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("1970/", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1970/2/", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("1970/", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1970/10/04/01", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1970/10/04/01/", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1970/10/04/01/01", ParserFoutCode.SYNTAX_ERROR, 5);
        testExp("1970/10/04/01/01/", ParserFoutCode.SYNTAX_ERROR, 5);
    }

    @Test
    public final void testClosures() throws ExpressieException {
        testExp("x + 2 = 12 WAARBIJ", ParserFoutCode.SYNTAX_ERROR, 18);
        testExp("x + 2 = 12 WAARBIJ x", ParserFoutCode.SYNTAX_ERROR, 20);
        testExp("x + 2 = 12 WAARBIJ x =", ParserFoutCode.SYNTAX_ERROR, 22);

    }

    @Test
    public final void testRekenOperatoren() throws ExpressieException {
        testExp("-", ParserFoutCode.SYNTAX_ERROR, 1);
        testExp("+", ParserFoutCode.SYNTAX_ERROR, 0);
        testExp("10+WAAR", ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT, 3);
        testExp("1970/03/01 + \"a\"", ParserFoutCode.NUMERIEKE_OF_DATUM_EXPRESSIE_VERWACHT, 13);
        testExp("10+", ParserFoutCode.SYNTAX_ERROR, 3);
        testExp("10+3-", ParserFoutCode.SYNTAX_ERROR, 5);
    }

    @Test
    public final void testLijsten() throws ExpressieException {
        testExp("{10, 20", ParserFoutCode.SYNTAX_ERROR, 7);
        testExp("{10 20", ParserFoutCode.SYNTAX_ERROR, 4);
    }

    @Test
    public final void testBooleanOperatoren() throws ExpressieException {
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
    public final void testVergelijkingsOperatoren() throws ExpressieException {
        testExp("10 = WAAR", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 5);
        testExp("ONWAAR = \"xyz\"", ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, 9);
        testExp("1 < WAAR", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 4);
        testExp("\"a\" <= 10", ParserFoutCode.STRING_EXPRESSIE_VERWACHT, 7);
        testExp("10 <> 1980/10/1", ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, 6);
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
        final Expressie pr;
        ExpressieException expressieException = null;
        try {
            pr = ExpressieParser.parse(input);
            System.out.println(pr);
        } catch (ExpressieException e) {
            e.printStackTrace();
            expressieException = e;
        }
        Assert.assertNotNull(expressieException);


//        Assert.assertFalse("Failing expression expected", pr.succes());
//        Assert.assertEquals(ExpressieType.ONBEKEND_TYPE, pr.getType());
//        final ParserFout f = new ParserFout(foutCode, input, foutPositiePlushaakjes);
//        Assert.assertEquals(f.toString(), pr.getFoutmelding());
//        final ParserFout fout = pr.getFout();
//        Assert.assertNotNull(fout);
//        Assert.assertEquals("Verkeerde foutcode", foutCode, fout.getFoutCode());
//        Assert.assertEquals("Foutpositie niet correct", foutPositiePlushaakjes, fout.getPositie());
    }
}
