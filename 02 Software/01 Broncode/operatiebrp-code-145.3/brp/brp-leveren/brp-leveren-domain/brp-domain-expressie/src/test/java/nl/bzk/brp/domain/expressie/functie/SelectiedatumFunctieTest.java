/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.expressie.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * FunctieVandaagTest.
 */
public class SelectiedatumFunctieTest {

    @Test
    public final void testFunctieAanroepen() throws ExpressieException {
        TestUtils.assertExceptie("SELECTIE_DATUM(10, 5)", "De functie SELECTIE_DATUM kan niet overweg met de argumenten: [Getal, Getal]");
    }

    @Test
    public void testSelectieDatumNietBeschikbaar() throws ExpressieException {
        TestUtils.assertExceptie("SELECTIE_DATUM()", "Geen waarde gevonden voor SELECTIE_DATUM()");
    }

    @Test
    public void testSelectieDatumBeschikbaar() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM()");
        final Context context = new Context();
        final Integer selectiedatum = 20100501;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(selectiedatum.intValue(), resultaat.alsInteger());
    }

        @Test
    public void testSelectieDatumPlusJaar() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM(10)");
        final Context context = new Context();
        final Integer selectiedatum = 20100501;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(20200501, resultaat.alsInteger());
    }

    @Test
    public void testSelectieDatumMinusJaar() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM(-10)");
        final Context context = new Context();
        final Integer selectiedatum = 20100501;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(20000501, resultaat.alsInteger());
    }

    @Test
    public void testSelectieDatumPlusJaarBetereOplossing() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM() + ^1/0/0");
        final Context context = new Context();
        final Integer selectiedatum = 20100501;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(20110501, resultaat.alsInteger());
    }

    @Test
    public void testSelectieDatumPlusMndBetereOplossing() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM() + ^0/1/0");
        final Context context = new Context();
        final Integer selectiedatum = 20100501;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(20100601, resultaat.alsInteger());
    }

    @Test
    public void testSelectieDatumPlusDagBetereOplossing() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM() + ^0/0/1");
        final Context context = new Context();
        final Integer selectiedatum = 20100502;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(20100503, resultaat.alsInteger());
    }


    @Test
    public void testSelectieDatumPlusJaarMaandDagBetereOplossing() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM() + ^1/12/15");
        final Context context = new Context();
        final Integer selectiedatum = 20100102;
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, selectiedatum);
        final DatumLiteral resultaat = (DatumLiteral) expressie.evalueer(context);
        Assert.assertEquals(20120117, resultaat.alsInteger());
    }

    @Test
    public void testGetType() throws ExpressieException {
        final Expressie expressie = ExpressieParser.parse("SELECTIE_DATUM()");
        Assert.assertEquals(ExpressieType.DATUM, expressie.getType(null));
    }
}
